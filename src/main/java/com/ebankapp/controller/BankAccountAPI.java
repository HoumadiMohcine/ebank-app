package com.ebankapp.controller;

import com.ebankapp.dto.*;
import com.ebankapp.entity.BankAccount;
import com.ebankapp.entity.Client;
import com.ebankapp.exception.BankAccountUnfoundException;
import com.ebankapp.exception.ClientUnfoundException;
import com.ebankapp.mapper.BankAccountMappers;
import com.ebankapp.service.BankAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class BankAccountAPI {
    private BankAccountService bankAccountService;
    private BankAccountMappers mappers;

    public BankAccountAPI(BankAccountService bankAccountService , BankAccountMappers mappers) {
        this.bankAccountService = bankAccountService;
        this.mappers = mappers;
    }

    @PostMapping("/accounts")
    public ResponseEntity<?> createBankAccount(@RequestParam String clientID  , @RequestParam long initialBalance) {
        BankAccount result = null;
        try{
            result = bankAccountService.saveBankAccount(clientID , initialBalance);
        }catch (ClientUnfoundException e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(mappers.fromBankAccount(result) , HttpStatus.CREATED);
    }

    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<?> getBankAccount(@PathVariable String accountId) {
        BankAccountDTO bankAccountDTO = null;
        try{
            bankAccountDTO = mappers.fromBankAccount(bankAccountService.getBankAccount(accountId));
        }catch (BankAccountUnfoundException e){
            return new ResponseEntity<>( e.getMessage() , HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>( bankAccountDTO , HttpStatus.OK);
    }
}
