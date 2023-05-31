package com.ebankapp.service.impl;

import com.ebankapp.entity.Client;
import com.ebankapp.exception.ClientUnfoundException;
import com.ebankapp.repositories.ClientRepository;
import com.ebankapp.utils.ClientUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {
    @InjectMocks
    private ClientServiceImpl clientService;
    @Mock
    private ClientRepository clientRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void saveClient() {
        Client client = ClientUtils.createClient(1L);
        Mockito.when(clientService.saveClient(client)).thenReturn(client);
        Client c = clientService.saveClient(client);
        // assert
        assertEquals(c.getName() , "mohcine1");
        Mockito.verify(clientRepository, Mockito.times(1)).save(client);
    }

    @Test
    void getClient() throws ClientUnfoundException {
        Mockito.when(clientRepository.findById(1L)).thenReturn(Optional.of(ClientUtils.createClient(1L)));
        Client client = clientService.getClient(1L);
        //assert
        assertEquals("mohcine1" , client.getName());

    }


}