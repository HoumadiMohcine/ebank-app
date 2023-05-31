package com.ebankapp.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Client {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String email;
    private String username;
    private String password;
    private Date creationDate;

    @OneToMany(mappedBy = "clientdetails")
    private List<BankAccount> bankAccounts;

}
