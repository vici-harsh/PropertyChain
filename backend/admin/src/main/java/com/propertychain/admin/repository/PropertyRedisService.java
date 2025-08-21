package com.propertychain.admin.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class PropertyRedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, String, Object> hashOps;

    @Autowired
    public PropertyRedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOps = redisTemplate.opsForHash();
    }

    public void saveProperty(Long propertyId, String address, String owner, String description) {
        String key = "property:" + propertyId;
        hashOps.put(key, "id", propertyId.toString());
        hashOps.put(key, "address", address);
        hashOps.put(key, "owner", owner);
        hashOps.put(key, "description", description);
        redisTemplate.expire(key, 30, TimeUnit.DAYS);
    }

    public Map<String, Object> getProperty(Long propertyId) {
        return hashOps.entries("property:" + propertyId);
    }

    public List<Map<String, Object>> getAllProperties() {
        Set<String> keys = redisTemplate.keys("property:*");
        List<Map<String, Object>> properties = new ArrayList<>();
        for (String key : keys) {
            properties.add(hashOps.entries(key));
        }
        return properties;
    }
}