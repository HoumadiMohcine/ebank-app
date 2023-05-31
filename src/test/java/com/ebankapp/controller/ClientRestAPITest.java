package com.ebankapp.controller;

import com.ebankapp.dto.ClientDTO;
import com.ebankapp.entity.Client;
import com.ebankapp.exception.ClientUnfoundException;
import com.ebankapp.mapper.BankAccountMappers;
import com.ebankapp.service.ClientService;
import com.ebankapp.utils.ClientUtils;
import com.ebankapp.utils.MappingUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;

@WebMvcTest(ClientRestAPI.class)
class ClientRestAPITest {
    @MockBean
    ClientService clientService;
    @MockBean
    BankAccountMappers mappers;
    @Autowired
    private MockMvc mvc;

    @Test
    void shouldReturnListOfClients() throws Exception {
        List<Client> list = new ArrayList<>();
        list.add(ClientUtils.createClient(1L));
        list.add(ClientUtils.createClient(2L));
        Mockito.when(clientService.getAllClient()).thenReturn(list);
        this.mvc
                .perform(get("/clients"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2))
                .andExpect(status().isOk());
    }

    @Test
    void ShouldReturn404WHenClientNotFound() throws Exception {
        Mockito.when(clientService.getClient(1L)).thenThrow(new ClientUnfoundException("le client introuvable"));
        this.mvc
                .perform(get("/clients/1"))
                .andExpect(status().isBadRequest());
    }


    @Test
    void shouldSaveClient() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Client client = ClientUtils.createClient(1L);
        ClientDTO clientDTO = ClientUtils.createClientDTO(1L);

        when(clientService.saveClient(any(Client.class))).thenReturn(client);
        when(mappers.fromClient(any(Client.class))).thenReturn(clientDTO);
        when(mappers.fromClientDTO(any(ClientDTO.class))).thenReturn(client);

        // execute
        MvcResult result = this.mvc.perform( post("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDTO)))
                .andReturn();

        // verify
        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.CREATED.value(), status, "Incorrect Response Status");

        // verify that service method was called once
        verify(clientService).saveClient(any(Client.class));

        //verify response id
        Client resultClient = (Client) MappingUtils.mapJsonToObject(result.getResponse().getContentAsString() , Client.class);
        assertNotNull(resultClient);
        assertEquals(1L, resultClient.getId().longValue());
    }
}