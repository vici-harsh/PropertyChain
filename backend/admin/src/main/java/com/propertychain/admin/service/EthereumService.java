package com.propertychain.admin.service;

import com.propertychain.admin.contracts.PropertyEscrow;
import com.propertychain.admin.contracts.PropertyOwnership;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import jakarta.annotation.PostConstruct;
import java.math.BigInteger;

@Service
public class EthereumService {
    private static final Logger logger = LoggerFactory.getLogger(EthereumService.class);

    private Web3j web3j;
    private PropertyOwnership ownershipContract;
    private PropertyEscrow escrowContract;

    @Value("${ethereum.node-url}")
    private String nodeUrl;

    @Value("${ethereum.owner-private-key}")
    private String ownerPrivateKey;

    @Value("${ethereum.buyer-private-key}")
    private String buyerPrivateKey;

    @Value("${ethereum.seller-private-key}")
    private String sellerPrivateKey;

    @Value("${ethereum.arbiter-private-key}")
    private String arbiterPrivateKey;

    @Value("${ethereum.ownership-contract-address}")
    private String ownershipContractAddress;

    @Value("${ethereum.escrow-contract-address}")
    private String escrowContractAddress;

    @PostConstruct
    public void init() {
        this.web3j = Web3j.build(new HttpService(nodeUrl));
        loadContracts();
    }

    private void loadContracts() {
        try {
            Credentials ownerCredentials = Credentials.create(ownerPrivateKey);
            ownershipContract = PropertyOwnership.load(
                    ownershipContractAddress,
                    web3j,
                    ownerCredentials,
                    new DefaultGasProvider()
            );
            // Verify contract code exists
            String ownershipCode = web3j.ethGetCode(ownershipContractAddress, DefaultBlockParameterName.LATEST).send().getCode();
            if ("0x".equals(ownershipCode)) {
                logger.error("No code found at PropertyOwnership address: {}", ownershipContractAddress);
                throw new RuntimeException("Invalid PropertyOwnership contract address");
            }
            logger.info("Loaded ownership contract at: {} with owner {}",
                    ownershipContractAddress, ownerCredentials.getAddress());

            Credentials buyerCredentials = Credentials.create(buyerPrivateKey);
            escrowContract = PropertyEscrow.load(
                    escrowContractAddress,
                    web3j,
                    buyerCredentials,
                    new DefaultGasProvider()
            );
            // Verify contract code exists
            String escrowCode = web3j.ethGetCode(escrowContractAddress, DefaultBlockParameterName.LATEST).send().getCode();
            if ("0x".equals(escrowCode)) {
                logger.error("No code found at PropertyEscrow address: {}", escrowContractAddress);
                throw new RuntimeException("Invalid PropertyEscrow contract address");
            }
            // Safely fetch buyer
            String buyerAddress = null;
            try {
                buyerAddress = escrowContract.buyer().send();
                if (buyerAddress == null || "0x0000000000000000000000000000000000000000".equals(buyerAddress)) {
                    logger.warn("Buyer not set in escrow contract at: {}", escrowContractAddress);
                } else {
                    logger.info("Loaded escrow contract at: {} with buyer {}",
                            escrowContractAddress, buyerAddress);
                }
            } catch (Exception e) {
                logger.warn("Failed to fetch buyer from escrow contract: {}", e.getMessage());
                // Allow startup to continue
            }
        } catch (Exception e) {
            logger.error("Contract loading failed: {}", e.getMessage());
            throw new RuntimeException("Contract loading failed", e);
        }
    }

    public Credentials getOwnerCredentials() {
        return Credentials.create(ownerPrivateKey);
    }

    public Credentials getBuyerCredentials() {
        return Credentials.create(buyerPrivateKey);
    }

    public Credentials getSellerCredentials() {
        return Credentials.create(sellerPrivateKey);
    }

    public Credentials getArbiterCredentials() {
        return Credentials.create(arbiterPrivateKey);
    }

    public Web3j getWeb3j() {
        return web3j;
    }

    public PropertyOwnership getOwnershipContract() {
        return ownershipContract;
    }

    public PropertyEscrow getEscrowContract() {
        return escrowContract;
    }

    public BigInteger addProperty(String propertyAddress, String description) throws Exception {
        try {
            // Use the contract wrapper directly with string parameters
            TransactionReceipt receipt = ownershipContract.addProperty(
                    getOwnerCredentials().getAddress(),
                    propertyAddress,
                    description
            ).send();

            if (!receipt.isStatusOK()) {
                logger.error("Transaction failed with status: {}", receipt.getStatus());
                throw new Exception("Transaction reverted with status: " + receipt.getStatus());
            }

            logger.info("PropertyAdded TX: {}", receipt.getTransactionHash());
            return ownershipContract.propertyCount().send();
        } catch (Exception e) {
            logger.error("Failed to add property: address={}, description={}, error={}",
                    propertyAddress, description, e.getMessage(), e);
            throw e;
        }
    }

    public String getOwner(BigInteger propertyId) throws Exception {
        return ownershipContract.getCurrentOwner(propertyId).send();
    }

    public void transferOwnership(BigInteger propertyId, String newOwner) throws Exception {
        TransactionReceipt receipt = ownershipContract.transferOwnership(
                propertyId,
                newOwner
        ).send();
        logger.info("OwnershipTransferred TX: {}", receipt.getTransactionHash());
    }

    public String deployNewEscrow(
            String seller,
            String arbiter,
            BigInteger propertyId,
            BigInteger value,
            BigInteger releaseTime
    ) throws Exception {
        PropertyEscrow escrow = PropertyEscrow.deploy(
                web3j,
                getBuyerCredentials(),
                new DefaultGasProvider(),
                value,
                seller,
                arbiter,
                propertyId,
                releaseTime
        ).send();
        String escrowAddress = escrow.getContractAddress();
        logger.info("New Escrow deployed at: {}", escrowAddress);
        return escrowAddress;
    }

    public void releaseFunds(BigInteger propertyId) throws Exception {
        TransactionReceipt receipt = escrowContract.releaseFunds().send();
        logger.info("Funds released for property {}", propertyId);
    }

    public String getOwnershipContractAddress() {
        return ownershipContractAddress;
    }
}