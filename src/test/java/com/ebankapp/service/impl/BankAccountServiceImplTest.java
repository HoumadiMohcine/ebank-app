package com.ebankapp.service.impl;

import com.ebankapp.entity.BankAccount;
import com.ebankapp.entity.Client;
import com.ebankapp.entity.Operation;
import com.ebankapp.enums.OperationType;
import com.ebankapp.exception.BankAccountUnfoundException;
import com.ebankapp.exception.ClientUnfoundException;
import com.ebankapp.mapper.BankAccountMappers;
import com.ebankapp.repositories.BankAccountRepository;
import com.ebankapp.repositories.ClientRepository;
import com.ebankapp.repositories.OperationRepository;
import com.ebankapp.service.BankAccountService;
import com.ebankapp.utils.BankAccountUtils;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        Client client = createClient();
        BankAccount bankAccount = BankAccountUtils.createBankAccount("1" , 5000);

        Mockito.when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        Mockito.when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(bankAccount);

        // when
        bankAccountService.saveBankAccount("1" , 5000);

        //verify
        Mockito.verify(bankAccountRepository, Mockito.times(1)).save(any(BankAccount.class));
    }

    @Test
    void getBankAccount() throws BankAccountUnfoundException {
        //given
        BankAccount bankAccount = new BankAccount();
        //bankAccount.setId("111");
        bankAccount.setBalance(300);
        bankAccount.setCreatedAt(new Date());

        // when
        Mockito.when(bankAccountRepository.findById("111")).thenReturn(Optional.of(bankAccount));
        BankAccount bankAccount1 = bankAccountService.getBankAccount("111");

        // verify
        assertEquals(bankAccount1.getBalance() , 300);
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