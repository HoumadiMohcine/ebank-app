package com.ebankapp.mapper;

import com.ebankapp.dto.ClientDTO;
import com.ebankapp.entity.Client;
import com.ebankapp.utils.ClientUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class BankAccountMappersTest {

    @InjectMocks
    BankAccountMappers mappers;

    @Test
    void fromClient() {
        Client client = ClientUtils.createClient(1L);
        ClientDTO clientDTO = mappers.fromClient(client);
        assertNotNull(clientDTO);
        assertEquals(clientDTO.getName() , client.getName());
    }

    @Test
    void fromClientDTO() {
        ClientDTO clientDTO = ClientUtils.createClientDTO(1L);
        Client client = mappers.fromClientDTO(clientDTO);

        assertNotNull(client);
        assertEquals(clientDTO.getName() , client.getName());
    }

    @Test
    void fromBankAccount() {
    }

    @Test
    void fromBankAccountDTO() {
    }

    @Test
    void fromAccountOperation() {
    }

    @Test
    void testFromClient() {
    }

    @Test
    void testFromClientDTO() {
    }

    @Test
    void testFromBankAccount() {
    }

    @Test
    void testFromBankAccountDTO() {
    }

    @Test
    void testFromAccountOperation() {
    }



}