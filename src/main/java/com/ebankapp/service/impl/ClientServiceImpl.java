package com.ebankapp.service.impl;

import com.ebankapp.entity.Client;
import com.ebankapp.exception.ClientUnfoundException;
import com.ebankapp.mapper.BankAccountMappers;
import com.ebankapp.repositories.ClientRepository;
import com.ebankapp.service.ClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {
    private ClientRepository clientRepository;
    @Override
    public Client saveClient(Client client) {
        System.out.println("client in save = " + client.getEmail());
        log.info("Saving new client");
        client.setCreationDate(new Date());
        Client client1 = clientRepository.save(client);
        return client1;
    }

    @Override
    public Client getClient(Long clientId) throws ClientUnfoundException {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new ClientUnfoundException("Customer Not found"));
    }

    @Override
    public List<Client> getAllClient() {
        return clientRepository.findAll();
    }
}
