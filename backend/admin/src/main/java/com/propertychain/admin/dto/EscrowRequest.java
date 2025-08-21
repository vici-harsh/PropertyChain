package com.propertychain.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EscrowRequest {
    @NotBlank(message = "Seller address is required")
    private String seller;

    @NotBlank(message = "Arbiter address is required")
    private String arbiter;

    @NotNull(message = "Value is required")
    private String value;

    @NotNull(message = "Release time is required")
    private String releaseTime;

    // Getters and setters
    public String getSeller() { return seller; }
    public void setSeller(String seller) { this.seller = seller; }
    public String getArbiter() { return arbiter; }
    public void setArbiter(String arbiter) { this.arbiter = arbiter; }
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    public String getReleaseTime() { return releaseTime; }
    public void setReleaseTime(String releaseTime) { this.releaseTime = releaseTime; }
}