package com.ebankapp.dto;

import lombok.Data;

@Data
public class WithdrawalDTO {
    private String accountId;
    private double amount;
    private String description;
}
