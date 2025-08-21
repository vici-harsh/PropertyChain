package com.propertychain.admin.dto;

import jakarta.validation.constraints.NotBlank;

public class AddPropertyRequest {
    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Description is required")
    private String description;

    // Getters and setters
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}