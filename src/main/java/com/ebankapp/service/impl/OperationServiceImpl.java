package com.ebankapp.service.impl;

import com.ebankapp.entity.BankAccount;
import com.ebankapp.entity.Operation;
import com.ebankapp.enums.OperationType;
import com.ebankapp.exception.BalanceInsufficientException;
import com.ebankapp.exception.BankAccountUnfoundException;
import com.ebankapp.repositories.BankAccountRepository;
import com.ebankapp.repositories.OperationRepository;
import com.ebankapp.service.OperationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class OperationServiceImpl implements OperationService {
    private BankAccountRepository bankAccountRepository;
    private OperationRepository operationRepository;
    @Override
    public void withdrawal(String accountId, double amount, String description) throws BankAccountUnfoundException, BalanceInsufficientException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountUnfoundException("BankAccount not found"));
        if(bankAccount.getBalance()<amount)
            throw new BalanceInsufficientException("Balance not sufficient");
        Operation accountOperation=new Operation();
        accountOperation.setOperationType(OperationType.WITHDRAWAL);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        operationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void deposit(String accountId, double amount, String description) throws BankAccountUnfoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountUnfoundException("BankAccount not found"));
        Operation accountOperation=new Operation();
        accountOperation.setOperationType(OperationType.DEPOSIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        operationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public List<Operation> accountHistory(String accountId) throws BankAccountUnfoundException {
        List<Operation> operations = operationRepository.findByBankAccountId(accountId);
        if (operations.isEmpty()) throw new BankAccountUnfoundException("no operations found");
        return operations;
    }
}
