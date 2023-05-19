package com.ebankapp.controller;

import com.ebankapp.dto.AccountOperationDTO;
import com.ebankapp.dto.DepositDTO;
import com.ebankapp.dto.WithdrawalDTO;
import com.ebankapp.entity.Operation;
import com.ebankapp.exception.BalanceInsufficientException;
import com.ebankapp.exception.BankAccountUnfoundException;
import com.ebankapp.mapper.BankAccountMappers;
import com.ebankapp.service.OperationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@Slf4j
@AllArgsConstructor
public class OperationRestAPI {
    private OperationService operationService;
    private BankAccountMappers mappers;

    @PostMapping("/operations/deposit")
    public ResponseEntity<?> Deposit(@RequestBody DepositDTO depositDTO){
        try{
            this.operationService.deposit(depositDTO.getAccountId(),depositDTO.getAmount(),depositDTO.getDescription());
        }catch(Exception  e){
            return new ResponseEntity<>( e.getMessage() , HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Opération terminée avec succès" , HttpStatus.OK);
    }

    @PostMapping("/operations/withdrawal")
    public ResponseEntity<?> withdrawal(@RequestBody WithdrawalDTO withdrawalDTO){
        try{
            this.operationService.withdrawal(withdrawalDTO.getAccountId(),withdrawalDTO.getAmount(),withdrawalDTO.getDescription());
        }catch(Exception  e){
            return new ResponseEntity<>( e.getMessage() , HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Opération terminée avec succès" , HttpStatus.OK);
    }

    @GetMapping("/operations/{accountId}")
    public ResponseEntity<?> getHistory(@PathVariable String accountId){
        List<AccountOperationDTO> accountOperationDTOS = null;
        try{
            accountOperationDTOS = operationService.accountHistory(accountId).stream().map(op->mappers.fromAccountOperation(op)).collect(Collectors.toList());
        }catch (BankAccountUnfoundException e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
        }
        return  new ResponseEntity<>( accountOperationDTOS , HttpStatus.OK);
    }
}
