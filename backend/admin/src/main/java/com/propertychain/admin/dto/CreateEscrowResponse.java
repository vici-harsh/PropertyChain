package com.propertychain.admin.dto;


import lombok.*;

@Data @AllArgsConstructor
public class CreateEscrowResponse {
    private Long propertyId;
    private String escrowAddress;
    private String txHash;
    private String status; // e.g., PENDING | OK
}
