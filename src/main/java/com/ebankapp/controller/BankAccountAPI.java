package com.ebankapp.controller;

import com.ebankapp.dto.*;
import com.ebankapp.entity.BankAccount;
import com.ebankapp.entity.Client;
import com.ebankapp.exception.BankAccountUnfoundException;
import com.ebankapp.exception.ClientUnfoundException;
import com.ebankapp.mapper.BankAccountMappers;
import com.ebankapp.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("accounts")
public class BankAccountAPI {
    private final BankAccountService bankAccountService;
    private final BankAccountMappers mappers;

    @PostMapping
    public ResponseEntity<?> createBankAccount(@RequestParam String clientID  , @RequestParam long initialBalance) throws ClientUnfoundException {
        BankAccount  result = bankAccountService.saveBankAccount(clientID , initialBalance);
        return new ResponseEntity<>(mappers.fromBankAccount(result) , HttpStatus.CREATED);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<?> getBankAccount(@PathVariable String accountId) throws BankAccountUnfoundException {
        BankAccountDTO bankAccountDTO = mappers.fromBankAccount(bankAccountService.getBankAccount(accountId));
        return new ResponseEntity<>( bankAccountDTO , HttpStatus.OK);
    }
}
