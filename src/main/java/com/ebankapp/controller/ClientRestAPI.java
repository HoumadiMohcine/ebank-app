package com.ebankapp.controller;

import com.ebankapp.dto.ClientDTO;
import com.ebankapp.entity.Client;
import com.ebankapp.exception.ClientUnfoundException;
import com.ebankapp.mapper.BankAccountMappers;
import com.ebankapp.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/clients")
public class ClientRestAPI {
    private final ClientService clientService;
    private final BankAccountMappers mappers;

    @GetMapping
    public ResponseEntity<?> clients(){
        List<ClientDTO> clients = clientService.getAllClient().stream().map(client->mappers.fromClient(client)).collect(Collectors.toList());
        if (clients.isEmpty()) return new ResponseEntity<>("Pas de clients" , HttpStatus.OK);
        return new ResponseEntity<>(clients , HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClient(@PathVariable(name = "id") Long clientId) throws ClientUnfoundException {
        ClientDTO client = mappers.fromClient(clientService.getClient(clientId));
        return new ResponseEntity<>(client , HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> saveClient(@RequestBody ClientDTO clientDTO){
        Client client = mappers.fromClientDTO(clientDTO);
        System.out.println("controlleur = " +client.getEmail());
        clientService.saveClient(client);
        return new ResponseEntity<>(mappers.fromClient(client) , HttpStatus.CREATED);
    }

}
