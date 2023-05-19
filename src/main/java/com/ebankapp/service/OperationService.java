package com.ebankapp.service;

import com.ebankapp.entity.Operation;
import com.ebankapp.exception.BalanceInsufficientException;
import com.ebankapp.exception.BankAccountUnfoundException;

import java.util.List;
import java.util.Optional;

public interface OperationService {
    void withdrawal(String accountId, double amount, String description) throws BankAccountUnfoundException, BalanceInsufficientException;
    void deposit(String accountId, double amount, String description) throws BankAccountUnfoundException;
    List<Operation> accountHistory(String accountId) throws BankAccountUnfoundException;

}
