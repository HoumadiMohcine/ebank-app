package com.ebankapp.utils;

import com.ebankapp.dto.ClientDTO;
import com.ebankapp.entity.Client;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Date;

public class ClientUtils {
    public static Client createClient(Long id){
        return Client.builder()
                .id(id)
                .name("mohcine" +id)
                .email("houmadi"+id+"@gmail.com")
                .address("XXXX" + id)
                .bankAccounts(new ArrayList<>())
                .build();
    }

    public static ClientDTO createClientDTO(Long id){
        return ClientDTO.builder()
                .id(id)
                .address("XXXX" + id)
                .name("mohcine" +id )
                .email("houmadi"+id+"@gmail.com")
                .build();
    }
}
