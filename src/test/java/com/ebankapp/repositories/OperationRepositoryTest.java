package com.ebankapp.repositories;

import com.ebankapp.entity.BankAccount;
import com.ebankapp.entity.Client;
import com.ebankapp.entity.Operation;
import com.ebankapp.utils.BankAccountUtils;
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
        BankAccount bankAccount = BankAccount.builder().id(accountId).balance(500).createdAt(new Date()).build();
        bankAccountRepository.save(bankAccount);

        Operation operation = Operation.builder().bankAccount(bankAccount).operationDate(new Date()).id(operationId).amount(200d).build();
        operationRepository.save(operation);

        //when
        List<Operation> operations = operationRepository.findByBankAccountId(accountId);
        // assert
        assertThat(operations).isNotNull();
        assertThat(operations.size() == 1);
    }
}