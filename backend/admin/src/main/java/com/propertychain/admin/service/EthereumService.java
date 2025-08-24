package com.propertychain.admin.service;

import com.propertychain.admin.contracts.PropertyEscrow;
import com.propertychain.admin.contracts.PropertyOwnership;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Keys;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Ethereum integration that tolerates the node being offline.
 * - Set ethereum.enabled=false to disable entirely
 * - Set ethereum.fail-fast=true to make startup fail when node is unreachable
 */
@Slf4j
@ConditionalOnProperty(name = "ethereum.enabled", havingValue = "true", matchIfMissing = true)
@Service
public class EthereumService {

    // ----- Config -----
    private final String nodeUrl;
    private final long chainId;
    private final String ownershipAddress;

    private final String ownerPk;
    private final String buyerPk;
    private final String sellerPk;
    private final String arbiterPk;

    // Optional runtime flags
    @Value("${ethereum.enabled:true}")
    private boolean enabled = true;

    @Value("${ethereum.fail-fast:false}")
    private boolean failFast;

    // ----- Web3 state -----
    private Web3j web3j;
    private volatile boolean online = false;

    private final ContractGasProvider gas = new DefaultGasProvider();

    // signer address (lowercase) -> creds/txManager
    private final Map<String, Credentials> credsByAddr = new ConcurrentHashMap<>();
    private final Map<String, TransactionManager> txMgrByAddr = new ConcurrentHashMap<>();

    private String ownerAddress;   // checksum
    private String buyerAddress;   // checksum
    private String sellerAddress;  // checksum
    private String arbiterAddress; // checksum

    private static final Logger log = LoggerFactory.getLogger(EthereumService.class);

    public EthereumService(
            @Value("${ethereum.node-url}") String nodeUrl,
            @Value("${ethereum.ownership-contract-address:}") String ownershipAddress,
            @Value("${ethereum.owner-private-key:}") String ownerPk,
            @Value("${ethereum.buyer-private-key:}") String buyerPk,
            @Value("${ethereum.seller-private-key:}") String sellerPk,
            @Value("${ethereum.arbiter-private-key:}") String arbiterPk,
            @Value("${ethereum.chain-id:1337}") long chainId
    ) {
        this.nodeUrl = nodeUrl;
        this.ownershipAddress = ownershipAddress;
        this.ownerPk = ownerPk;
        this.buyerPk = buyerPk;
        this.sellerPk = sellerPk;
        this.arbiterPk = arbiterPk;
        this.chainId = chainId;

        init();
    }

    // ----- Init / health -----

    private void init() {
        enabled = true;
        if (!enabled) {
            online = false;
            log.warn("EthereumService disabled via configuration (ethereum.enabled=false).");
            return;
        }
        try {
            Web3jService http = new HttpService(nodeUrl);
            this.web3j = Web3j.build(http);

            this.ownerAddress   = addPk(ownerPk);
            this.buyerAddress   = addPk(buyerPk);
            this.sellerAddress  = addPk(sellerPk);
            this.arbiterAddress = addPk(arbiterPk);

            String client = web3j.web3ClientVersion().send().getWeb3ClientVersion();
            log.info("Ethereum client: {}", client);
            log.info("Owner:   {}", ownerAddress);
            log.info("Buyer:   {}", buyerAddress);
            log.info("Seller:  {}", sellerAddress);
            log.info("Arbiter: {}", arbiterAddress);

            if (ownershipAddress == null || ownershipAddress.isBlank()) {
                log.warn("Missing ethereum.ownership-contract-address (load/deploy functions will fail if used).");
            }
            online = true;
        } catch (Exception e) {
            online = false;
            String msg = "Ethereum init failed (will continue without chain): " + e.getMessage();
            if (failFast) {
                throw new IllegalStateException(msg, e);
            } else {
                log.warn(msg);
            }
        }
    }

    public boolean isOnline() {
        return enabled && online && web3j != null;
    }

    private void requireOnline() {
        if (!isOnline()) {
            throw new IllegalStateException("Ethereum is disabled/offline; start Ganache or enable it in config.");
        }
    }

    // ----- Helpers -----

    private static String lc(String addr) { return addr == null ? "" : addr.toLowerCase(Locale.ROOT); }

    /** Register a private key, return checksum address (or null if blank/invalid). */
    private String addPk(String pk) {
        try {
            if (pk == null || pk.isBlank()) return null;
            Credentials c = Credentials.create(pk);
            String addr = Keys.toChecksumAddress(c.getAddress());
            credsByAddr.put(lc(addr), c);
            txMgrByAddr.put(lc(addr), new RawTransactionManager(web3j, c, chainId));
            return addr;
        } catch (Exception e) {
            log.warn("Ignoring invalid private key.");
            return null;
        }
    }

    private TransactionManager txManagerFor(String addressOrNull) {
        if (addressOrNull != null) {
            TransactionManager tm = txMgrByAddr.get(lc(addressOrNull));
            if (tm != null) return tm;
        }
        // fallback to owner
        return txMgrByAddr.get(lc(ownerAddress));
    }

    // ----- Contract loaders -----

    public PropertyOwnership getOwnershipContract() {
        requireOnline();
        if (ownershipAddress == null || ownershipAddress.isBlank()) {
            throw new IllegalStateException("Missing ethereum.ownership-contract-address");
        }
        return PropertyOwnership.load(ownershipAddress, web3j, txManagerFor(ownerAddress), gas);
    }

    private PropertyOwnership loadOwnershipAs(String signer) {
        requireOnline();
        if (ownershipAddress == null || ownershipAddress.isBlank()) {
            throw new IllegalStateException("Missing ethereum.ownership-contract-address");
        }
        return PropertyOwnership.load(ownershipAddress, web3j, txManagerFor(signer), gas);
    }

    // ----- Public API used by your services/controllers -----

    /**
     * Adds a property on-chain. Returns the new on-chain propertyId.
     * Signs with the configured contract owner by default.
     */
    public BigInteger addProperty(String propertyAddress, String description) throws Exception {
        requireOnline();
        PropertyOwnership c = loadOwnershipAs(ownerAddress);
        BigInteger before = c.propertyCount().send();
        TransactionReceipt r = c.addProperty(ownerAddress, propertyAddress, description).send();
        BigInteger after = c.propertyCount().send();
        log.info("addProperty tx={}", r.getTransactionHash());
        return after; // monotonically incrementing id
    }

    /**
     * Transfers ownership on-chain (signed by owner by default).
     * If your contract requires msg.sender = current owner, ensure ownerAddress is the current owner.
     */
    public void transferOwnership(BigInteger propertyId, String newOwner) throws Exception {
        requireOnline();
        PropertyOwnership c = loadOwnershipAs(ownerAddress);
        TransactionReceipt r = c.transferOwnership(propertyId, newOwner).send();
        log.info("transferOwnership({}) -> {} tx={}", propertyId, newOwner, r.getTransactionHash());
    }

    /**
     * Deploy a new escrow contract. Signed by the configured "buyer" key.
     * @return deployed escrow address
     */
    public String deployNewEscrow(String seller, String arbiter,
                                  BigInteger propertyId, BigInteger valueWei, BigInteger releaseTime) throws Exception {
        requireOnline();
        TransactionManager buyerTm = txManagerFor(buyerAddress);

        // Signature expected by your generated wrapper:
        // deploy(Web3j, TransactionManager, ContractGasProvider, BigInteger initialWeiValue, String seller, String arbiter, BigInteger propertyId, BigInteger releaseTime)
        PropertyEscrow esc = PropertyEscrow
                .deploy(web3j, buyerTm, gas, valueWei, seller, arbiter, propertyId, releaseTime)
                .send();

        String txHash = esc.getTransactionReceipt().isPresent()
                ? esc.getTransactionReceipt().get().getTransactionHash()
                : "0x";
        log.info("Escrow deployed @{} (tx={}) for property {}", esc.getContractAddress(), txHash, propertyId);
        return esc.getContractAddress();
    }

    /** Expose owner credentials when needed by other components. */
    public Credentials getOwnerCredentials() {
        return credsByAddr.get(lc(ownerAddress));
    }

    // Optional getters if you need them elsewhere
    public String getOwnerAddress()   { return ownerAddress; }
    public String getBuyerAddress()   { return buyerAddress; }
    public String getSellerAddress()  { return sellerAddress; }
    public String getArbiterAddress() { return arbiterAddress; }
}
