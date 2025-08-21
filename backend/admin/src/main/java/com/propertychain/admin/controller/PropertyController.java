package com.propertychain.admin.controller;

import com.propertychain.admin.service.PropertyService;
import com.propertychain.admin.dto.EscrowRequest;
import com.propertychain.admin.dto.AddPropertyRequest;
import com.propertychain.admin.dto.TransferRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @PostMapping
    public String addProperty(@RequestBody @Valid AddPropertyRequest req) throws Exception {
        propertyService.addPropertyOnChain(req.getAddress(), req.getDescription());
        return "Property added successfully with ID: " + propertyService.getAllProperties().size();
    }

    @PostMapping("/{id}/transfer")
    public String transferOwnership(@PathVariable Long id, @RequestBody @Valid TransferRequest req) throws Exception {
        propertyService.transferOwnership(id, req.getNewOwner());
        return "Ownership transferred successfully for property ID: " + id;
    }

    @PostMapping("/{id}/escrow")
    public String createEscrow(@PathVariable Long id, @RequestBody @Valid EscrowRequest req) throws Exception {
        BigInteger value = new BigInteger(req.getValue());
        BigInteger releaseTime = new BigInteger(req.getReleaseTime());
        String escrowAddress = propertyService.createEscrow(id, req.getSeller(), req.getArbiter(), value, releaseTime);
        return "Escrow deployed at: " + escrowAddress + " for property ID: " + id;
    }

    @GetMapping
    public List<Map<String, Object>> listAll() {
        return propertyService.getAllProperties();
    }

    @GetMapping("/{id}/history")
    public List<String> getHistory(@PathVariable Long id) throws Exception {
        return propertyService.getPropertyHistory(id);
    }
}