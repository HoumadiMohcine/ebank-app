package com.ebankapp.service.impl;

import com.ebankapp.entity.BankAccount;
import com.ebankapp.entity.Client;
import com.ebankapp.entity.Operation;
import com.ebankapp.enums.OperationType;
import com.ebankapp.exception.BalanceInsufficientException;
import com.ebankapp.exception.BankAccountUnfoundException;
import com.ebankapp.repositories.BankAccountRepository;
import com.ebankapp.repositories.OperationRepository;
import com.ebankapp.utils.BankAccountUtils;
import com.ebankapp.utils.ClientUtils;
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
        // given
        Client client = ClientUtils.createClient(1L);
        BankAccount bankAccount = BankAccountUtils.createBankAccount("1" , 4000);
        client.getBankAccounts().add(bankAccount);
        // when
        Mockito.when(bankAccountRepository.findById("1")).thenReturn(Optional.of(bankAccount));
        operationService.withdrawal("1" , 500 , "testing Withdrawal operation");
        Optional<BankAccount> result = bankAccountRepository.findById("1");
        // assert
        assertEquals( result.get().getBalance() , 3500);
    }

    @Test
    void deposit() throws BankAccountUnfoundException {
        // given
        Client client = ClientUtils.createClient(1L);
        BankAccount bankAccount = BankAccountUtils.createBankAccount("1" , 4000);
        client.getBankAccounts().add(bankAccount);
        // when
        Mockito.when(bankAccountRepository.findById("1")).thenReturn(Optional.of(bankAccount));
        operationService.deposit("1" , 500 , "testing Withdrawal operation");
        Optional<BankAccount> result = bankAccountRepository.findById("1");
        // assert
        assertEquals( result.get().getBalance() , 4500);
    }

    @Test
    void accountHistory() throws BankAccountUnfoundException {
        //given
        BankAccount bankAccount = BankAccountUtils.createBankAccount("1" , 3000);
        List<Operation> operations = new ArrayList<>();
        operations.add(Operation.builder().id(1L).operationDate(new Date()).amount(200d).bankAccount(bankAccount).operationType(OperationType.DEPOSIT).description("desc DEPOSIT").build());
        operations.add(Operation.builder().id(2L).operationDate(new Date()).amount(100d).bankAccount(bankAccount).operationType(OperationType.WITHDRAWAL).description("desc WITHDRAWAL").build());
        // when
        Mockito.when(operationRepository.findByBankAccountId("111")).thenReturn(operations);
        List<Operation> result = operationService.accountHistory("111");
        // verify
        assertEquals(2, result.size());
        Mockito.verify(operationRepository, Mockito.times(1)).findByBankAccountId("111");
    }
}