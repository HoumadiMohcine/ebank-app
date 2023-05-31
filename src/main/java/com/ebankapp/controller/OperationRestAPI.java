package com.ebankapp.controller;

import com.ebankapp.dto.AccountOperationDTO;
import com.ebankapp.dto.OperationDTO;
import com.ebankapp.exception.BalanceInsufficientException;
import com.ebankapp.exception.BankAccountUnfoundException;
import com.ebankapp.mapper.BankAccountMappers;
import com.ebankapp.service.OperationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("operations")
public class OperationRestAPI {
    private final OperationService operationService;
    private final BankAccountMappers mappers;

    @PostMapping("/deposit")
    public ResponseEntity<?> Deposit(@RequestBody OperationDTO depositDTO) throws BankAccountUnfoundException {
        this.operationService.deposit(depositDTO.getAccountId(),depositDTO.getAmount(),depositDTO.getDescription());
        return new ResponseEntity<>("Opération terminée avec succès" , HttpStatus.OK);
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<?> withdrawal(@RequestBody OperationDTO withdrawalDTO) throws BalanceInsufficientException, BankAccountUnfoundException {
        this.operationService.withdrawal(withdrawalDTO.getAccountId(),withdrawalDTO.getAmount(),withdrawalDTO.getDescription());
        return new ResponseEntity<>("Opération terminée avec succès" , HttpStatus.OK);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<?> getHistory(@PathVariable String accountId) throws BankAccountUnfoundException {
        List<AccountOperationDTO> accountOperationDTOS = operationService.accountHistory(accountId).stream().map(op->mappers.fromAccountOperation(op)).collect(Collectors.toList());
        return  new ResponseEntity<>( accountOperationDTOS , HttpStatus.OK);
    }
}
