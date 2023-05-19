package com.ebankapp.repositories;

import com.ebankapp.entity.BankAccount;
import com.ebankapp.entity.Operation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest(properties = {"spring.jpa.properties.javax.persistence.validation.mode=none"})
class OperationRepositoryTest {
    @Autowired
    OperationRepository operationRepository;

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Test
    void findByBankAccountId() {
        // given
        String accountId = "11";
        Long operationId = 122L;
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(accountId);
        bankAccount.setBalance(500);
        bankAccount.setCreatedAt(new Date());
        bankAccountRepository.save(bankAccount);


        Operation operation = new Operation();
        operation.setBankAccount(bankAccount);
        operation.setOperationDate(new Date());
        operation.setId(operationId);
        operation.setAmount(Double.valueOf(200));

        operationRepository.save(operation);

        //when
        List<Operation> operations = operationRepository.findByBankAccountId(accountId);

        assertThat(operations).isNotNull();
    }
}