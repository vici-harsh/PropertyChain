package com.propertychain.main.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PropertyRedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, String, Object> hashOps;
    private final SetOperations<String, Object> setOps;

    @Autowired
    public PropertyRedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOps = redisTemplate.opsForHash();
        this.setOps = redisTemplate.opsForSet();
    }

    public void saveProperty(Long propertyId, String address, String owner, String description) {
        String key = "property:" + propertyId;
        hashOps.put(key, "id", propertyId.toString());
        hashOps.put(key, "address", address);
        hashOps.put(key, "owner", owner);
        hashOps.put(key, "description", description);
        setOps.add("properties", propertyId.toString());
    }

    public Map<String, Object> getProperty(Long propertyId) {
        return hashOps.entries("property:" + propertyId);
    }

    public List<Map<String, Object>> getAllProperties() {
        Set<Object> ids = setOps.members("properties");
        List<Map<String, Object>> list = new ArrayList<>();
        if(ids != null) {
            for (Object id : ids) {
                list.add(getProperty(Long.parseLong(id.toString())));
            }
        }
        return list;
    }
}
