package com.ebankapp.utils;

import com.ebankapp.dto.BankAccountDTO;
import com.ebankapp.dto.ClientDTO;
import com.ebankapp.entity.BankAccount;
import com.ebankapp.entity.Client;

import java.util.Date;

public class BankAccountUtils {
    public static BankAccount createBankAccount(String id , double balance){
        Client client = ClientUtils.createClient(1L);
        return BankAccount.builder().id(id).balance(balance).createdAt(new Date()).clientdetails(client).build();
    }

    public static BankAccountDTO createBankAccountDTO(String id , double balance){
        ClientDTO clientDTO = ClientUtils.createClientDTO(1L);
        return BankAccountDTO.builder()
                .id(id).balance(balance).createdAt(new Date()).clientDTO(clientDTO).build();
    }
}
