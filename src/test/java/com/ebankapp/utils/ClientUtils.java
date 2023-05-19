package com.ebankapp.utils;

import com.ebankapp.dto.ClientDTO;
import com.ebankapp.entity.Client;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ClientUtils {
    public static Client createClient(Long id){
        Client client = new Client();
        client.setId(id);
        client.setName("mohcine" +id);
        client.setEmail("houmadi"+id+"@gmail.com");
        client.setAddress("XXXX" + id);
        return client;
    }

    public static ClientDTO createClientDTO(Long id){
        return new ClientDTO(id , "mohcine" +id , "XXXX" + id , "houmadi"+id+"@gmail.com");
    }
}
