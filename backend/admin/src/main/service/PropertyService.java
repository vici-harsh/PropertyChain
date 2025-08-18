package com.propertychain.admin.service;

import com.propertychain.admin.repository.PropertyRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropertyService {

    @Autowired
    private PropertyRedisService redisService;

    @Autowired
    private EthereumService ethereumService;

    public void addPropertyOnChain(String address, String description) throws Exception {
        Long propertyId = ethereumService.addProperty(address, description);
        redisService.saveProperty(propertyId, address, ethereumService.getOwner(propertyId), description);
    }

    public void transferOwnership(Long propertyId, String newOwner) throws Exception {
        ethereumService.transferOwnership(propertyId, newOwner);
        redisService.saveProperty(propertyId,
                (String) redisService.getProperty(propertyId).get("address"),
                newOwner,
                (String) redisService.getProperty(propertyId).get("description"));
    }

    public java.util.List<java.util.Map<String, Object>> getAllProperties() {
        return redisService.getAllProperties();
    }
}
