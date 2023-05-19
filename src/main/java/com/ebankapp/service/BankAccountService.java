package com.ebankapp.service;



import com.ebankapp.dto.AccountHistoryDTO;
import com.ebankapp.dto.AccountOperationDTO;
import com.ebankapp.dto.BankAccountDTO;
import com.ebankapp.dto.ClientDTO;
import com.ebankapp.entity.BankAccount;
import com.ebankapp.entity.Client;
import com.ebankapp.entity.Operation;
import com.ebankapp.exception.BalanceInsufficientException;
import com.ebankapp.exception.BankAccountUnfoundException;
import com.ebankapp.exception.ClientUnfoundException;
import org.springframework.data.domain.Page;

import java.util.List;
public interface BankAccountService {
    BankAccount saveBankAccount( String clientID , long initialBalance) throws ClientUnfoundException;
    BankAccount getBankAccount(String accountId) throws BankAccountUnfoundException;

    Page<Operation> getAccountHistory(String accountId, int page, int size) throws BankAccountUnfoundException;

}
