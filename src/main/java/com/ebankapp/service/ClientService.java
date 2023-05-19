package com.ebankapp.service;

import com.ebankapp.entity.Client;
import com.ebankapp.exception.ClientUnfoundException;

import java.util.List;

public interface ClientService {
    Client saveClient(Client client);
    Client getClient(Long customerId) throws ClientUnfoundException;
    List<Client> getAllClient();
}
