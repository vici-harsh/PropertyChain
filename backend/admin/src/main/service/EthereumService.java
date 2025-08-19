package com.propertychain.admin.service;

import com.propertychain.admin.contracts.PropertyEscrow;
import com.propertychain.admin.contracts.PropertyOwnership;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
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
    private Credentials credentials;

    @Value("${ethereum.node-url}")
    private String nodeUrl;

    @Value("${ethereum.ownership-contract-address}")
    private String ownershipContractAddress;

    @Value("${ethereum.escrow-contract-address}")
    private String escrowContractAddress;

    @Value("${ethereum.private-key}")
    private String privateKey;

    @PostConstruct
    public void init() {
        this.web3j = Web3j.build(new HttpService(nodeUrl));
        this.credentials = Credentials.create(privateKey);
        loadContracts();
    }

    private void loadContracts() {
        try {
            // Load ownership contract
            ownershipContract = PropertyOwnership.load(
                    ownershipContractAddress,
                    web3j,
                    credentials,
                    new DefaultGasProvider()
            );
            logger.info("Loaded ownership contract at: {}", ownershipContractAddress);

            // Load escrow contract
            escrowContract = PropertyEscrow.load(
                    escrowContractAddress,
                    web3j,
                    credentials,
                    new DefaultGasProvider()
            );
            logger.info("Loaded escrow contract at: {}", escrowContractAddress);

        } catch (Exception e) {
            logger.error("Contract loading failed: {}", e.getMessage());
        }
    }

    public Long addProperty(String propertyAddress, String description) throws Exception {
        // Use the deployer's address as initial owner
        String ownerAddress = credentials.getAddress();

        TransactionReceipt receipt = ownershipContract.addProperty(
                ownerAddress,
                propertyAddress,
                description
        ).send();

        logger.info("PropertyAdded TX: {}", receipt.getTransactionHash());
        return ownershipContract.propertyCount().send().longValue();
    }

    public String getOwner(Long propertyId) throws Exception {
        return ownershipContract.getCurrentOwner(BigInteger.valueOf(propertyId)).send();
    }

    public void transferOwnership(Long propertyId, String newOwner) throws Exception {
        TransactionReceipt receipt = ownershipContract.transferOwnership(
                BigInteger.valueOf(propertyId),
                newOwner
        ).send();
        logger.info("OwnershipTransferred TX: {}", receipt.getTransactionHash());
    }

    public void createEscrow(Long propertyId, String seller, String arbiter,
                             BigInteger value, BigInteger releaseTime) throws Exception {
        TransactionReceipt receipt = escrowContract.createEscrow(
                seller,
                arbiter,
                BigInteger.valueOf(propertyId),
                releaseTime,
                value
        ).send();
        logger.info("EscrowCreated TX: {}", receipt.getTransactionHash());
    }
}