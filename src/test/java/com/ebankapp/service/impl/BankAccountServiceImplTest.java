package com.ebankapp.service.impl;

import com.ebankapp.entity.BankAccount;
import com.ebankapp.entity.Client;
import com.ebankapp.exception.BankAccountUnfoundException;
import com.ebankapp.repositories.BankAccountRepository;
import com.ebankapp.repositories.ClientRepository;
import com.ebankapp.utils.BankAccountUtils;
import com.ebankapp.utils.ClientUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceImplTest {
    @Mock
    private BankAccountRepository bankAccountRepository;
    @Mock
    private ClientRepository clientRepository;
    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveBankAccount() throws Exception {
        // given
        Client client = ClientUtils.createClient(1L);
        BankAccount bankAccount = BankAccountUtils.createBankAccount("1" , 5000);
        // when
        Mockito.when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        Mockito.when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(bankAccount);
        bankAccountService.saveBankAccount("1" , 5000);
        //verify
        Mockito.verify(bankAccountRepository, Mockito.times(1)).save(any(BankAccount.class));
    }

    @Test
    void getBankAccount() throws BankAccountUnfoundException {
        //given
        BankAccount bankAccount = BankAccountUtils.createBankAccount("111" , 300);
        // when
        Mockito.when(bankAccountRepository.findById("111")).thenReturn(Optional.of(bankAccount));
        BankAccount bankAccount1 = bankAccountService.getBankAccount("111");
        // verify
        assertEquals(bankAccount1.getBalance() , 300);
    }
}