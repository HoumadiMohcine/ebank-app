package com.ebankapp.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BankAccount {
    @Id
    private String id;
    private double balance;
    private Date createdAt;
    @ManyToOne
    private Client clientdetails;
    @OneToMany(mappedBy = "bankAccount",fetch = FetchType.LAZY)
    private List<Operation> operations;


}
