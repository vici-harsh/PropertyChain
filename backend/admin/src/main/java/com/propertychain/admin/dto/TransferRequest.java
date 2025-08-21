package com.propertychain.admin.dto;

import jakarta.validation.constraints.NotBlank;

public class TransferRequest {
    @NotBlank(message = "New owner address is required")
    private String newOwner;

    // Getters and setters
    public String getNewOwner() { return newOwner; }
    public void setNewOwner(String newOwner) { this.newOwner = newOwner; }
}