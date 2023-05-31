package com.ebankapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
public class OperationDTO {
    private String accountId;
    private double amount;
    private String description;
}
