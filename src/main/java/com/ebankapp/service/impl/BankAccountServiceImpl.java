package com.ebankapp.service.impl;

import com.ebankapp.entity.BankAccount;
import com.ebankapp.entity.Client;
import com.ebankapp.entity.Operation;
import com.ebankapp.exception.BankAccountUnfoundException;
import com.ebankapp.exception.ClientUnfoundException;
import com.ebankapp.repositories.BankAccountRepository;
import com.ebankapp.repositories.ClientRepository;
import com.ebankapp.repositories.OperationRepository;
import com.ebankapp.service.BankAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {
    private ClientRepository clientRepository;
    private BankAccountRepository bankAccountRepository;
    private OperationRepository operationRepository;


    @Override
    public BankAccount saveBankAccount(String clientID , long initialBalance) throws ClientUnfoundException{
        Client client=clientRepository.findById(Long.valueOf(clientID)).orElse(null);
        if(client==null){
            throw new ClientUnfoundException("client not found");
        }
        BankAccount bankAccount = BankAccount.builder()
                .id(UUID.randomUUID().toString())
                .clientdetails(client)
                .balance(initialBalance)
                .createdAt(new Date()).build();

        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public BankAccount getBankAccount(String accountId) throws BankAccountUnfoundException {
        return bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountUnfoundException("BankAccount not found"));

    }

    @Override
    public Page<Operation> getAccountHistory(String accountId, int page, int size) throws BankAccountUnfoundException {
        BankAccount bankAccount= this.getBankAccount(accountId);
        if(bankAccount==null) throw new BankAccountUnfoundException("Account not Found");
        return operationRepository.findByBankAccountIdOrderByOperationDateDesc(accountId, PageRequest.of(page, size));
    }
}
