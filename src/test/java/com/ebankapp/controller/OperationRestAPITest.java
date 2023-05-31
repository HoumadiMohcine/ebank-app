package com.ebankapp.controller;

import com.ebankapp.dto.OperationDTO;
import com.ebankapp.entity.BankAccount;
import com.ebankapp.exception.BalanceInsufficientException;
import com.ebankapp.exception.BankAccountUnfoundException;
import com.ebankapp.mapper.BankAccountMappers;
import com.ebankapp.repositories.BankAccountRepository;
import com.ebankapp.service.OperationService;
import com.ebankapp.utils.BankAccountUtils;
import com.ebankapp.utils.ClientUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(OperationRestAPI.class)
@Slf4j
class OperationRestAPITest {
    @MockBean
    private OperationService operationService;
    @Autowired
    private MockMvc mvc;
    @MockBean
    private BankAccountRepository bankAccountRepository;
    @MockBean
    BankAccountMappers mappers;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String OPERATIONS_URL = "/operations";
    private final String DEPOSIT_URL = "/deposit";
    private final String WITHDRAWAL_URL = "/withdrawal";

    @Test
    void shouldDoDepositWithSuccess() throws Exception {
        BankAccount bankAccount = BankAccountUtils.createBankAccount("1", 3000);
        OperationDTO depositDTO = OperationDTO.builder()
                .description("testing deposit")
                .amount(300)
                .accountId(bankAccount.getId()).build();

        MvcResult result = this.mvc.perform( post(OPERATIONS_URL + DEPOSIT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(depositDTO)))
                .andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status, "Incorrect Response Status");


        // verify that service method was called once
        verify(operationService).deposit("1" , 300 , "testing deposit");

        //verify response id
        assertNotNull(result.getResponse().getContentAsString());
        assertEquals("Opération terminée avec succès", result.getResponse().getContentAsString());
    }

    @Test
    void shouldReturn404WhenBankAccountNotFound() throws Exception {
        BankAccount bankAccount = BankAccountUtils.createBankAccount("1", 3000);
        OperationDTO depositDTO = OperationDTO.builder()
                .description("testing deposit")
                .amount(300)
                .accountId(bankAccount.getId()).build();

        doThrow(BankAccountUnfoundException.class).when(operationService).deposit(bankAccount.getId() , 300 , "testing deposit");
        this.mvc.perform( post(OPERATIONS_URL + DEPOSIT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(depositDTO))).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldDoWithdrawalWithSuccess() throws Exception {
        BankAccount bankAccount = BankAccountUtils.createBankAccount("1", 3000);
        OperationDTO depositDTO = OperationDTO.builder()
                .description("testing withdrawal")
                .amount(300)
                .accountId(bankAccount.getId()).build();

        MvcResult result = this.mvc.perform( post(OPERATIONS_URL + WITHDRAWAL_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(depositDTO)))
                .andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status, "Incorrect Response Status");


        // verify that service method was called once
        verify(operationService).withdrawal("1" , 300 , "testing withdrawal");
        String response = result.getResponse().getContentAsString();
        //verify response id
        assertNotNull(response);
        assertEquals("Opération terminée avec succès", response);
    }

    @Test
    void shouldReturn404WhenBalanceNotSufficient() throws Exception {
        BankAccount bankAccount = BankAccountUtils.createBankAccount("1", 200);
        when(bankAccountRepository.findById("1")).thenReturn(Optional.of(bankAccount));
        OperationDTO depositDTO = OperationDTO.builder()
                .description("testing withdrawal")
                .amount(300)
                .accountId(bankAccount.getId()).build();

        doThrow(BalanceInsufficientException.class).when(operationService).withdrawal(bankAccount.getId() , 300 , "testing withdrawal");
        this.mvc.perform( post(OPERATIONS_URL + WITHDRAWAL_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(depositDTO))).andExpect(MockMvcResultMatchers.status().isBadRequest());
        verify(operationService).withdrawal(bankAccount.getId() , 300 , "testing withdrawal");
    }

}