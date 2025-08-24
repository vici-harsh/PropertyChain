package com.propertychain.admin.dto;


import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigInteger;

@Data
public class EscrowRequest {
    @NotBlank
    @Pattern(regexp = "^0x[a-fA-F0-9]{40}$", message = "Invalid Ethereum address")
    private String seller;

    @NotBlank
    @Pattern(regexp = "^0x[a-fA-F0-9]{40}$", message = "Invalid Ethereum address")
    private String arbiter;

    @NotNull @Positive
    private java.math.BigInteger valueWei;   // FE converts ETH->wei before sending

    @NotNull @Positive
    private Long releaseTime;                // epoch seconds


    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getArbiter() {
        return arbiter;
    }

    public void setArbiter(String arbiter) {
        this.arbiter = arbiter;
    }

    public BigInteger getValueWei() {
        return valueWei;
    }

    public void setValueWei(BigInteger valueWei) {
        this.valueWei = valueWei;
    }

    public Long getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Long releaseTime) {
        this.releaseTime = releaseTime;
    }
}
