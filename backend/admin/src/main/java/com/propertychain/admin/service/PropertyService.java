package com.propertychain.admin.service;

import com.propertychain.admin.contracts.PropertyOwnership;
import com.propertychain.admin.repository.PropertyRedisService;
import com.propertychain.admin.event.EventProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PropertyService {
    private static final Logger logger = LoggerFactory.getLogger(PropertyService.class);

    @Autowired
    private PropertyRedisService redisService;

    @Autowired
    private EthereumService ethereumService;

    @Autowired
    private EventProducer eventProducer;

    public Long addPropertyOnChain(String address, String description) throws Exception {
        Long propertyId = ethereumService.addProperty(address, description);
        String owner = ethereumService.getOwner(propertyId);

        redisService.saveProperty(propertyId, address, owner, description);
        logger.info("PropertyAdded: {}, {}, {}, {}", propertyId, address, owner, description);

        String eventJson = String.format("{\"type\":\"PropertyAdded\",\"id\":%d,\"address\":\"%s\",\"owner\":\"%s\",\"description\":\"%s\"}",
                propertyId, address, owner, description);
        eventProducer.publishEvent(eventJson);

        return propertyId;
    }

    public void transferOwnership(Long propertyId, String newOwner) throws Exception {
        Map<String, Object> property = redisService.getProperty(propertyId);
        if (property == null || property.get("owner") == null) {
            throw new IllegalArgumentException("Property not found or owner not set");
        }
        String currentOwner = (String) property.get("owner");

        ethereumService.transferOwnership(propertyId, newOwner);
        redisService.saveProperty(propertyId, (String) property.get("address"), newOwner, (String) property.get("description"));
        logger.info("OwnershipTransferred: {} from {} to {}", propertyId, currentOwner, newOwner);

        String eventJson = String.format("{\"type\":\"OwnershipTransferred\",\"id\":%d,\"from\":\"%s\",\"to\":\"%s\"}",
                propertyId, currentOwner, newOwner);
        eventProducer.publishEvent(eventJson);
    }

    public List<Map<String, Object>> getAllProperties() {
        List<Map<String, Object>> properties = redisService.getAllProperties();

        properties.forEach(property -> {
            Long propertyId = Long.parseLong(property.get("id").toString());
            try {
                property.put("blockchainOwner", ethereumService.getOwner(propertyId));
            } catch (Exception e) {
                logger.error("Error fetching owner for property {}: {}", propertyId, e.getMessage());
                property.put("blockchainOwner", "Error fetching");
            }
        });

        return properties;
    }

    public String createEscrow(Long propertyId, String seller, String arbiter, BigInteger value, BigInteger releaseTime) throws Exception {
        String escrowAddress = ethereumService.deployNewEscrow(seller, arbiter, propertyId, value, releaseTime);
        logger.info("Escrow created for property {} at {}", propertyId, escrowAddress);

        String eventJson = String.format("{\"type\":\"EscrowCreated\",\"id\":%d,\"seller\":\"%s\",\"arbiter\":\"%s\",\"value\":\"%s\",\"releaseTime\":\"%s\",\"address\":\"%s\"}",
                propertyId, seller, arbiter, value.toString(), releaseTime.toString(), escrowAddress);
        eventProducer.publishEvent(eventJson);

        return escrowAddress;
    }

    public void releaseFunds(Long propertyId) throws Exception {
        ethereumService.releaseFunds(propertyId);
        logger.info("Funds released for property {}", propertyId);

        String eventJson = String.format("{\"type\":\"FundsReleased\",\"id\":%d}", propertyId);
        eventProducer.publishEvent(eventJson);
    }

    public List<String> getPropertyHistory(Long id) throws Exception {
        List<String> history = new ArrayList<>();
        PropertyOwnership ownership = PropertyOwnership.load(
                ethereumService.getOwnershipContractAddress(),
                ethereumService.getWeb3j(),
                ethereumService.getOwnerCredentials(),
                new DefaultGasProvider()
        );
        history.add("Initial owner set for property " + id);
        return history;
    }
}