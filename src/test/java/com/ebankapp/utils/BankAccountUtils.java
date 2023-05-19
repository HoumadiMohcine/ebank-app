package com.ebankapp.utils;

import com.ebankapp.dto.BankAccountDTO;
import com.ebankapp.dto.ClientDTO;
import com.ebankapp.entity.BankAccount;
import com.ebankapp.entity.Client;

import java.util.Date;

public class BankAccountUtils {
    public static BankAccount createBankAccount(String id , double balance){
        Client client = ClientUtils.createClient(1L);
        BankAccount bankAccount = new BankAccount(id , balance , new Date() , client , null  );
        return bankAccount;
    }

    public static BankAccountDTO createBankAccountDTO(String id , double balance){
        ClientDTO clientDTO = ClientUtils.createClientDTO(1L);
        BankAccountDTO bankAccountDTO = new BankAccountDTO(id , balance , new Date() , clientDTO);
        return bankAccountDTO;
    }
}
