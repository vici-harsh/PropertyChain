package com.propertychain.admin.service;

import com.propertychain.admin.repository.PropertyRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Map;

@Service
@Transactional
public class PropertyService {
    private static final Logger logger = LoggerFactory.getLogger(PropertyService.class);

    @Autowired
    private PropertyRedisService redisService;

    @Autowired
    private EthereumService ethereumService;

    public void addPropertyOnChain(String address, String description) throws Exception {
        Long propertyId = ethereumService.addProperty(address, description);
        String owner = ethereumService.getOwner(propertyId);

        redisService.saveProperty(propertyId, address, owner, description);
        logger.info("PropertyAdded: {}, {}, {}, {}",
                propertyId, address, owner, description);
    }

    public void transferOwnership(Long propertyId, String newOwner) throws Exception {
        Map<String, Object> property = redisService.getProperty(propertyId);
        String currentOwner = (String) property.get("owner");

        ethereumService.transferOwnership(propertyId, newOwner);
        redisService.saveProperty(
                propertyId,
                (String) property.get("address"),
                newOwner,
                (String) property.get("description")
        );
        logger.info("OwnershipTransferred: {} from {} to {}",
                propertyId, currentOwner, newOwner);
    }

    public void createEscrow(Long propertyId, String seller, String arbiter,
                             BigInteger value, BigInteger releaseTime) throws Exception {
        ethereumService.createEscrow(propertyId, seller, arbiter, value, releaseTime);
        logger.info("EscrowCreated: property={}, seller={}, value={}",
                propertyId, seller, value);
    }
}