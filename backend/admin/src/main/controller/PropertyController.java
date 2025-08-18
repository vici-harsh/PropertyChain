package com.propertychain.main.controller;

import com.propertychain.main.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @PostMapping
    public String addProperty(@RequestBody Map<String, String> req) throws Exception {
        propertyService.addPropertyOnChain(req.get("address"), req.get("description"));
        return "Property added successfully";
    }

    @PostMapping("/{id}/transfer")
    public String transferOwnership(@PathVariable Long id, @RequestBody Map<String, String> req) throws Exception {
        propertyService.transferOwnership(id, req.get("newOwner"));
        return "Ownership transferred successfully";
    }

    @GetMapping
    public List<Map<String, Object>> listAll() {
        return propertyService.getAllProperties();
    }
}
