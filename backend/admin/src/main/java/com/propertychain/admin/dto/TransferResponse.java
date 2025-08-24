package com.propertychain.admin.dto;


import lombok.*;

@Data @AllArgsConstructor
public class TransferResponse {
    private Long propertyId;
    private String txHash;
    private String status;
}
