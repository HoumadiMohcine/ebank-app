package com.ebankapp.controller;

import com.ebankapp.dto.BankAccountDTO;
import com.ebankapp.dto.ClientDTO;
import com.ebankapp.entity.BankAccount;
import com.ebankapp.exception.BankAccountUnfoundException;
import com.ebankapp.exception.ClientUnfoundException;
import com.ebankapp.mapper.BankAccountMappers;
import com.ebankapp.service.BankAccountService;
import com.ebankapp.utils.BankAccountUtils;
import com.ebankapp.utils.ClientUtils;
import com.ebankapp.utils.MappingUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BankAccountAPI.class)
class BankAccountAPITest {
    @MockBean
    private BankAccountService bankAccountService;
    @MockBean
    private BankAccountMappers mappers;
    @Autowired
    private MockMvc mvc;

    @Test
    void shouldCreateBankAccount() throws Exception {
        BankAccount bankAccount = BankAccountUtils.createBankAccount("1", 5000);
        BankAccountDTO bankAccountDTO = BankAccountUtils.createBankAccountDTO("1" , 5000);

        when(mappers.fromBankAccount(any(BankAccount.class))).thenReturn(bankAccountDTO);
        when(mappers.fromBankAccountDTO(any(BankAccountDTO.class))).thenReturn(bankAccount);
        when(bankAccountService.saveBankAccount("1" , 5000)).thenReturn(bankAccount);

        // execute
        MvcResult result = this.mvc.perform( post("/accounts")
                        .param("clientID" , "1")
                        .param("initialBalance" , "5000")).andReturn();

        // verify
        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.CREATED.value(), status, "Incorrect Response Status");

        // verify that service method was called once
        verify(bankAccountService).saveBankAccount("1" , 5000);

        //verify response id
        BankAccountDTO resultBankAccount = (BankAccountDTO) MappingUtils.mapJsonToObject(result.getResponse().getContentAsString() , BankAccountDTO.class);
        assertNotNull(resultBankAccount);
        assertEquals("1", resultBankAccount.getId());
        assertEquals("mohcine1", resultBankAccount.getClientDTO().getName());
    }


    @Test
    void shouldReturn404WhenClientNotFound() throws Exception {
        ClientDTO clientDTO = ClientUtils.createClientDTO(1L);

        BankAccountDTO bankAccountDTO = new BankAccountDTO("1" , 5000 , null , clientDTO );
        when(bankAccountService.saveBankAccount("1" , 5000)).thenThrow(new ClientUnfoundException("Client est introuvable"));
        // execute
        this.mvc.perform( post("/accounts")
                .param("clientID" , "1")
                .param("initialBalance" , "5000")).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn404WhenGettingBankAccountNotFound() throws Exception {
        when(bankAccountService.getBankAccount("1")).thenThrow(new BankAccountUnfoundException("le compte est Introuvable"));
        this.mvc
                .perform(get("/accounts/1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBankAccountById() throws Exception {
        BankAccount bankAccount = BankAccountUtils.createBankAccount("1" , 2000);
        BankAccountDTO bankAccountDTO = BankAccountUtils.createBankAccountDTO("1" , 2000);
        Mockito.when(bankAccountService.getBankAccount("1")).thenReturn(bankAccount);
        Mockito.when(mappers.fromBankAccount(bankAccount)).thenReturn(bankAccountDTO);
        Mockito.when(mappers.fromBankAccountDTO(bankAccountDTO)).thenReturn(bankAccount);

        this.mvc
                .perform(get("/accounts/1"))
                .andExpect(status().isOk());
    }
}