package com.ebankapp.controller;

import com.ebankapp.dto.ClientDTO;
import com.ebankapp.entity.Client;
import com.ebankapp.exception.ClientUnfoundException;
import com.ebankapp.mapper.BankAccountMappers;
import com.ebankapp.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
public class ClientRestAPI {
    @Autowired
    private ClientService clientService;

    @Autowired
    private BankAccountMappers mappers;

    @GetMapping("/clients")
    public ResponseEntity<?> clients(){
        List<ClientDTO> clients = clientService.getAllClient().stream().map(client->mappers.fromClient(client)).collect(Collectors.toList());
        if (clients.isEmpty()) return new ResponseEntity<>("Pas de clients" , HttpStatus.OK);
        return new ResponseEntity<>(clients , HttpStatus.OK);
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<?> getClient(@PathVariable(name = "id") Long clientId) {
        ClientDTO client = null;
        try{
            client = mappers.fromClient(clientService.getClient(clientId));
        }catch (ClientUnfoundException ex){
            return new ResponseEntity<>( ex.getMessage() , HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(client , HttpStatus.OK);
    }
    @PostMapping("/clients")
    public ResponseEntity<?> saveClient(@RequestBody ClientDTO clientDTO){
        Client client = mappers.fromClientDTO(clientDTO);
        System.out.println("controlleur = " +client.getEmail());
        clientService.saveClient(client);
        return new ResponseEntity<>(mappers.fromClient(client) , HttpStatus.CREATED);
    }

    Client createClient(){
        Client client = new Client();
        client.setName("mohcine1");
        client.setEmail("houmadi1@gmail.com");
        return client;
    }
}
