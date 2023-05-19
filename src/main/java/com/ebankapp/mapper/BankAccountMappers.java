package com.ebankapp.mapper;


import com.ebankapp.dto.AccountOperationDTO;
import com.ebankapp.dto.BankAccountDTO;
import com.ebankapp.dto.ClientDTO;
import com.ebankapp.entity.BankAccount;
import com.ebankapp.entity.Client;
import com.ebankapp.entity.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMappers {
    public ClientDTO fromClient(Client client){
        ClientDTO clientDTO=new ClientDTO();
        BeanUtils.copyProperties(client,clientDTO);
        System.out.println("mapper   " + clientDTO.getEmail());
        return  clientDTO;
    }

    public Client fromClientDTO(ClientDTO clientDTO){
        Client client=new Client();
        BeanUtils.copyProperties(clientDTO,client);
        System.out.println(client.getName());
        return  client;
    }

    public BankAccountDTO fromBankAccount(BankAccount bankAccount){
        BankAccountDTO bankAccountDTO=new BankAccountDTO();
        BeanUtils.copyProperties(bankAccount,bankAccountDTO);
        bankAccountDTO.setClientDTO(fromClient(bankAccount.getClientdetails()));
        return bankAccountDTO;
    }

    public BankAccount fromBankAccountDTO(BankAccountDTO bankAccountDTO){
        BankAccount bankAccount=new BankAccount();
        BeanUtils.copyProperties(bankAccountDTO,bankAccount);
        bankAccount.setClientdetails(fromClientDTO(bankAccountDTO.getClientDTO()));
        return bankAccount;
    }

    public AccountOperationDTO fromAccountOperation(Operation operation){
        AccountOperationDTO accountOperationDTO=new AccountOperationDTO();
        BeanUtils.copyProperties(operation,accountOperationDTO);
        return accountOperationDTO;
    }
}
