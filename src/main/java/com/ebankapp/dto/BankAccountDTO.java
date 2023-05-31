package com.ebankapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccountDTO {
    private String id;
    private double balance;
    private Date createdAt;
    private ClientDTO clientDTO;
}
