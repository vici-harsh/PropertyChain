package com.propertychain.admin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.propertychain.admin.dto.AddPropertyRequest;
import com.propertychain.admin.model.HistoryEvent;
import com.propertychain.admin.model.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class PropertyService {

    private static final Logger logger = LoggerFactory.getLogger(PropertyService.class);

    private final PropertyRedisService redisService;
    private final EthereumService ethereumService;
    private final EventProducer eventProducer;
    private final ObjectMapper om;

    public PropertyService(PropertyRedisService redisService,
                           EthereumService ethereumService,
                           EventProducer eventProducer,
                           ObjectMapper om) {
        this.redisService = redisService;
        this.ethereumService = ethereumService;
        this.eventProducer = eventProducer;
        this.om = om;
    }

    /**
     * NEW: Persist FULL AddPropertyRequest (all fields) and add on-chain to get the ID.
     */
    public Long addProperty(AddPropertyRequest req) throws Exception {
        // 1) Call chain to create and obtain on-chain id (keeps your current ID strategy)
        BigInteger onChainId = ethereumService.addProperty(req.getAddressLine(),
                req.getDescription() == null ? "" : req.getDescription());
        Long propertyId = onChainId.longValue();

        // 2) Build full Property snapshot
        Property p = new Property();
        p.setId(propertyId);
        p.setAddressLine(req.getAddressLine());
        p.setCity(req.getCity());
        p.setState(req.getState());
        p.setPostalCode(req.getPostalCode());
        p.setDescription(req.getDescription());
        p.setPrice(req.getPrice());
        p.setPropertyType(req.getPropertyType());
        p.setBedrooms(req.getBedrooms());
        p.setBathrooms(req.getBathrooms());
        p.setAreaSqft(req.getAreaSqft());
        p.setImages(req.getImages()); // may be null; fine for Redis JSON
        p.setOwnerWallet(req.getOwnerWallet() != null
                ? req.getOwnerWallet()
                : ethereumService.getOwnerCredentials().getAddress());
        p.setLat(req.getLat());
        p.setLng(req.getLng());
        p.setCreatedAt(Instant.now());

        // 3) Save full object to Redis
        redisService.saveProperty(p);

        // 4) History & event (best-effort)
        HistoryEvent evt = PropertyRedisService.now("ADDED", null, p.getOwnerWallet(), null, null, "onChainId=" + propertyId);
        redisService.appendHistory(propertyId, evt);
        try { eventProducer.publishEvent("PROPERTY_ADDED:" + propertyId); }
        catch (Exception e) { logger.warn("Kafka publish failed (PROPERTY_ADDED:{}): {}", propertyId, e.getMessage()); }

        logger.info("Property added id={} address='{}' owner={}", propertyId, p.getAddressLine(), p.getOwnerWallet());
        return propertyId;
    }

    /**
     * Existing method kept for compatibility (adds minimal snapshot).
     * Consider switching your controller to call addProperty(req) instead.
     */
    /** Add a property on-chain and persist an off-chain snapshot. */
    public Long addPropertyOnChain(String addressLine, String description) throws Exception {
        BigInteger onChainId = ethereumService.addProperty(addressLine, description);
        Long propertyId = onChainId.longValue();

        String ownerAddress = ethereumService.getOwnerCredentials().getAddress();

        Property p = new Property();
        p.setId(propertyId);
        p.setAddressLine(addressLine);
        p.setOwnerWallet(ownerAddress);
        p.setDescription(description);
        p.setCreatedAt(Instant.now());
        redisService.saveProperty(p);

        // NEW: write history
        try {
            HistoryEvent evt = PropertyRedisService.now(
                    "ADDED", null, ownerAddress, null, null, "onChainId=" + propertyId);
            redisService.appendHistory(propertyId, evt);
        } catch (Exception e) {
            logger.warn("Failed to append history for addProperty: {}", e.getMessage());
        }

        try { eventProducer.publishEvent("PROPERTY_ADDED:" + propertyId); }
        catch (Exception e) { logger.warn("Kafka publish failed (PROPERTY_ADDED:{}): {}", propertyId, e.getMessage()); }

        logger.info("Property added id={} address='{}' owner={}", propertyId, addressLine, ownerAddress);
        return propertyId;
    }


    /** Transfer ownership on-chain and mirror in Redis. */
    /** Transfer ownership on-chain and mirror in Redis. */
    public void transferOwnership(Long propertyId, String newOwner) throws Exception {
        // Capture previous owner (if we have it)
        String prevOwner = redisService.getProperty(propertyId)
                .map(Property::getOwnerWallet).orElse(null);

        // On-chain transfer
        ethereumService.transferOwnership(BigInteger.valueOf(propertyId), newOwner);

        // Update snapshot
        redisService.getProperty(propertyId).ifPresent(p -> {
            p.setOwnerWallet(newOwner);
            redisService.saveProperty(p);
        });

        // NEW: write history
        try {
            HistoryEvent evt = PropertyRedisService.now(
                    "TRANSFER", prevOwner, newOwner, null, null, null);
            redisService.appendHistory(propertyId, evt);
        } catch (Exception e) {
            logger.warn("Failed to append history for transfer: {}", e.getMessage());
        }

        // Event
        try { eventProducer.publishEvent("PROPERTY_TRANSFERRED:" + propertyId + "->" + newOwner); }
        catch (Exception e) { logger.warn("Kafka publish failed (PROPERTY_TRANSFERRED:{}): {}", propertyId, e.getMessage()); }

        logger.info("Ownership transferred id={} newOwner={}", propertyId, newOwner);
    }


    /** Deploy escrow for this property. */
    /** Deploy escrow for this property. */
    public String createEscrow(Long propertyId, String seller, String arbiter,
                               BigInteger value, BigInteger releaseTime) throws Exception {
        String escrowAddress = ethereumService.deployNewEscrow(
                seller, arbiter, BigInteger.valueOf(propertyId), value, releaseTime
        );

        // NEW: write history
        try {
            String details = "escrow=" + escrowAddress + ", valueWei=" + value;
            HistoryEvent evt = PropertyRedisService.now(
                    "ESCROW_CREATED", null, null, null, null, details);
            redisService.appendHistory(propertyId, evt);
        } catch (Exception e) {
            logger.warn("Failed to append history for escrow: {}", e.getMessage());
        }

        // Event
        try { eventProducer.publishEvent("ESCROW_CREATED:" + propertyId + ":" + escrowAddress); }
        catch (Exception e) { logger.warn("Kafka publish failed (ESCROW_CREATED:{}): {}", propertyId, e.getMessage()); }

        logger.info("Escrow created id={} address={} valueWei={}", propertyId, escrowAddress, value);
        return escrowAddress;
    }


    // ----- Read APIs used by the controller -----

    private Map<String, Object> toMap(Property p) {
        var m = new LinkedHashMap<String, Object>();
        m.put("id", p.getId());
        m.put("address", p.getAddressLine());
        m.put("owner", p.getOwnerWallet());
        m.put("description", p.getDescription());
        m.put("price", p.getPrice());
        m.put("city", p.getCity());
        m.put("state", p.getState());
        m.put("postalCode", p.getPostalCode());
        m.put("type", p.getPropertyType());
        m.put("bedrooms", p.getBedrooms());
        m.put("bathrooms", p.getBathrooms());
        m.put("areaSqft", p.getAreaSqft());
        if (p.getImages() != null) m.put("images", p.getImages());
        if (p.getLat() != null)    m.put("lat", p.getLat());
        if (p.getLng() != null)    m.put("lng", p.getLng());
        m.put("createdAt", p.getCreatedAt() == null ? null : p.getCreatedAt().toString());
        return m;
    }

    public Map<String,Object> getProperty(Long id) {
        return redisService.getProperty(id).map(this::toMap).orElse(Map.of());
    }

    public List<Map<String,Object>> getAllProperties() {
        return redisService.getAll().stream().map(this::toMap).toList();
    }

    /** Placeholder history. Replace with on-chain log scan if desired. */
    /** Return human-readable history lines from Redis. */
    public List<String> getPropertyHistory(Long id) {
        var events = redisService.getHistory(id);
        var out = new java.util.ArrayList<String>(events.size());
        for (HistoryEvent e : events) {
            StringBuilder sb = new StringBuilder();
            sb.append(e.getAt() != null ? e.getAt().toString() : "").append(" ");
            sb.append(e.getType() != null ? e.getType() : "EVENT");
            if (e.getFrom() != null) sb.append(" from ").append(e.getFrom());
            if (e.getTo() != null)   sb.append(" to ").append(e.getTo());
            if (e.getTx() != null)   sb.append(" tx=").append(e.getTx());
            if (e.getDetails() != null) sb.append(" ").append(e.getDetails());
            out.add(sb.toString().trim());
        }
        return out;
    }


    // (Not used by controller now, but kept in case others call it.)
    private void publishEvent(String type, Property p, HistoryEvent evt) {
        try {
            Map<String,Object> payload = new LinkedHashMap<>();
            payload.put("type", type);
            payload.put("property", toMap(p));
            payload.put("event", Map.of(
                    "type", evt.getType(),
                    "from", evt.getFrom(),
                    "to", evt.getTo(),
                    "tx", evt.getTx(),
                    "block", evt.getBlock(),
                    "at", evt.getAt() == null ? null : evt.getAt().toString(),
                    "details", evt.getDetails()
            ));
            eventProducer.publishEvent(om.writeValueAsString(payload));
        } catch (Exception e) {
            logger.warn("publishEvent failed: {}", e.getMessage());
        }
    }
}
