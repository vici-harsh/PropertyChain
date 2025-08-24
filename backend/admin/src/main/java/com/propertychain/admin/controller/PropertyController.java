package com.propertychain.admin.controller;

import com.propertychain.admin.dto.AddPropertyRequest;
import com.propertychain.admin.dto.EscrowRequest;
import com.propertychain.admin.dto.TransferRequest;
import com.propertychain.admin.service.PropertyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/properties")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PropertyController {

    private final PropertyService service;

    public PropertyController(PropertyService service) {
        this.service = service;
    }

    /** POST /api/properties */
    @PostMapping
    public ResponseEntity<Map<String, Object>> add(@Valid @RequestBody AddPropertyRequest req) throws Exception {
        // ✅ DTO has addressLine
        Long id = service.addProperty(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", id));
    }

    /** GET /api/properties */
    @GetMapping
    public List<Map<String, Object>> list() {
        return service.getAllProperties();
    }

    /** GET /api/properties/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> get(@PathVariable Long id) {
        Map<String, Object> p = service.getProperty(id); // make sure PropertyService has this wrapper
        if (p == null || p.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(p);
    }

    /** GET /api/properties/{id}/history */
    @GetMapping("/{id}/history")
    public List<String> history(@PathVariable Long id) throws Exception {
        return service.getPropertyHistory(id);
    }

    /** POST /api/properties/{id}/transfer */
    @PostMapping("/{id}/transfer")
    public ResponseEntity<Map<String, Object>> transfer(@PathVariable Long id,
                                                        @Valid @RequestBody TransferRequest req) throws Exception {
        service.transferOwnership(id, req.getNewOwner());
        return ResponseEntity.ok(Map.of("propertyId", id, "status", "OK"));
    }

    /** POST /api/properties/{id}/escrow */
    @PostMapping("/{id}/escrow")
    public ResponseEntity<Map<String, Object>> createEscrow(@PathVariable Long id,
                                                            @Valid @RequestBody EscrowRequest req) throws Exception {
        // ✅ DTO has valueWei (BigInteger) and releaseTime (Long)
        BigInteger valueWei = req.getValueWei();
        BigInteger release  = BigInteger.valueOf(req.getReleaseTime());

        String escrowAddress = service.createEscrow(id, req.getSeller(), req.getArbiter(), valueWei, release);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("propertyId", id, "escrowAddress", escrowAddress, "status", "OK"));
    }
}
