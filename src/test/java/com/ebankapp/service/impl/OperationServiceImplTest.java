package com.ebankapp.service.impl;

import com.ebankapp.entity.BankAccount;
import com.ebankapp.entity.Client;
import com.ebankapp.entity.Operation;
import com.ebankapp.enums.OperationType;
import com.ebankapp.exception.BalanceInsufficientException;
import com.ebankapp.exception.BankAccountUnfoundException;
import com.ebankapp.repositories.BankAccountRepository;
import com.ebankapp.repositories.OperationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class OperationServiceImplTest {
    @InjectMocks
    OperationServiceImpl operationService;

    @Mock
    BankAccountRepository bankAccountRepository;
    @Mock
    OperationRepository operationRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldDoWithdrawalWhenBalanceIsEnough() throws BalanceInsufficientException, BankAccountUnfoundException {
        Client client = createClient();
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBalance(4000);
        bankAccount.setCreatedAt(new Date());
        bankAccount.setClientdetails(client);
        client.getBankAccounts().add(bankAccount);

        Mockito.when(bankAccountRepository.findById("1")).thenReturn(Optional.of(bankAccount));
        operationService.withdrawal("1" , 500 , "testing Withdrawal operation");

        Optional<BankAccount> result = bankAccountRepository.findById("1");

        assertEquals( result.get().getBalance() , 3500);
    }

    @Test
    void deposit() throws BankAccountUnfoundException {
        Client client = createClient();
        BankAccount bankAccount = new BankAccount();
        //bankAccount.setId("1");
        bankAccount.setBalance(4000);
        bankAccount.setCreatedAt(new Date());
        bankAccount.setClientdetails(client);
        client.getBankAccounts().add(bankAccount);

        Mockito.when(bankAccountRepository.findById("1")).thenReturn(Optional.of(bankAccount));
        operationService.deposit("1" , 500 , "testing Withdrawal operation");

        Optional<BankAccount> result = bankAccountRepository.findById("1");

        assertEquals( result.get().getBalance() , 4500);
    }

    @Test
    void accountHistory() throws BankAccountUnfoundException {
        //given
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBalance(3000);
        bankAccount.setCreatedAt(new Date());

        List<Operation> operations = new ArrayList<>();
        operations.add(new Operation(1L , new Date() , (double) 200, bankAccount , OperationType.DEPOSIT , "desc1"));
        operations.add(new Operation(2L , new Date() , (double) 100, bankAccount , OperationType.WITHDRAWAL , "desc2"));
        Mockito.when(operationRepository.findByBankAccountId("111")).thenReturn(operations);
        // when
        List<Operation> result = operationService.accountHistory("111");
        // verify
        assertEquals(2, result.size());
        Mockito.verify(operationRepository, Mockito.times(1)).findByBankAccountId("111");
    }

    private Client createClient(){
        Client client = new Client();
        client.setId(1L);
        client.setName("mohcine1");
        client.setAddress("XXXX1");
        client.setEmail("houmadi1@gmail.com");
        client.setCreationDate(new Date());
        client.setBankAccounts(new ArrayList<>());
        return client;
    }
}