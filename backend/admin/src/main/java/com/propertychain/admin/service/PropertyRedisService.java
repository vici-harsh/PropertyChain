package com.propertychain.admin.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.propertychain.admin.model.HistoryEvent;
import com.propertychain.admin.model.Property;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PropertyRedisService {

    private final RedisTemplate<String, Object> redis;
    private final ObjectMapper om;

    // keys
    private static final String KEY_SEQ  = "prop:seq";
    private static final String KEY_IDX  = "prop:index";
    private static final String KEY_PROP = "prop:item:";
    private static final String KEY_HIST = "prop:hist:";
    private static final String KEY_MAP_ONCHAIN = "prop:onchain:"; // offId -> onChainId

    public PropertyRedisService(RedisTemplate<String, Object> redis, ObjectMapper om) {
        this.redis = redis;
        this.om = om;
    }

    public synchronized Long nextId() {
        try {
            Long val = redis.opsForValue().increment(KEY_SEQ);
            return (val == null ? 1L : val);
        } catch (DataAccessException e) {
            return new AtomicLong().incrementAndGet();
        }
    }

    // Save a full Property object
    public void saveProperty(Property p) {
        redis.opsForValue().set(KEY_PROP + p.getId(), p);
        redis.opsForSet().add(KEY_IDX, p.getId());
    }

    // Convenience overload (used by recovery/service)
    public void saveProperty(Long id, String addressLine, String ownerWallet, String description) {
        Property p = new Property();
        p.setId(id);
        p.setAddressLine(addressLine);
        p.setOwnerWallet(ownerWallet);
        p.setDescription(description);
        p.setCreatedAt(Instant.now());
        saveProperty(p);
    }

    public Optional<Property> getProperty(Long id) {
        Object obj = redis.opsForValue().get(KEY_PROP + id);
        if (obj == null) return Optional.empty();
        return Optional.ofNullable(om.convertValue(obj, Property.class));
    }

    public List<Property> getAll() {
        Set<Object> ids = redis.opsForSet().members(KEY_IDX);
        if (ids == null || ids.isEmpty()) return List.of();
        List<Property> out = new ArrayList<>();
        for (Object id : ids) getProperty(Long.valueOf(id.toString())).ifPresent(out::add);
        out.sort(Comparator.comparing(Property::getCreatedAt).reversed());
        return out;
    }

    public void appendHistory(Long propertyId, HistoryEvent evt) {
        String key = KEY_HIST + propertyId;
        List<HistoryEvent> current = getHistory(propertyId);
        current.add(evt);
        redis.opsForValue().set(key, current);
    }

    public List<HistoryEvent> getHistory(Long propertyId) {
        Object obj = redis.opsForValue().get(KEY_HIST + propertyId);
        if (obj == null) return new ArrayList<>();
        return om.convertValue(obj, new TypeReference<List<HistoryEvent>>() {});
    }

    public static HistoryEvent now(String type, String from, String to, String tx, Long block, String details) {
        HistoryEvent e = new HistoryEvent();
        e.setType(type);
        e.setFrom(from);
        e.setTo(to);
        e.setTx(tx);
        e.setBlock(block);
        e.setAt(Instant.now());
        e.setDetails(details);
        return e;
    }

    public void putOnChainId(Long offId, java.math.BigInteger onId) {
        redis.opsForValue().set(KEY_MAP_ONCHAIN + offId, onId.toString());
    }
    public Optional<java.math.BigInteger> getOnChainId(Long offId) {
        Object s = redis.opsForValue().get(KEY_MAP_ONCHAIN + offId);
        if (s == null) return Optional.empty();
        try { return Optional.of(new java.math.BigInteger(s.toString())); }
        catch (Exception e) { return Optional.empty(); }
    }


}
