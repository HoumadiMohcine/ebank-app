package com.ebankapp.entity;

import com.ebankapp.enums.OperationType;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Operation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date operationDate;
    private Double amount;
    @ManyToOne
    private BankAccount bankAccount;
    private OperationType operationType;
    private String description;
}
