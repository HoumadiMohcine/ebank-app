package com.ebankapp.repositories;

import com.ebankapp.entity.Client;
import com.ebankapp.utils.ClientUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest(
        properties = {
                "spring.jpa.properties.javax.persistence.validation.mode=none"
        }
)
class ClientRepositoryTest {
    @Autowired
    private ClientRepository clientRepository;
    @Test
    void itShouldSearchClientsByName() {
        // given
        Client client = ClientUtils.createClient(1L);
        clientRepository.save(client);
        // when
        List<Client> clients = clientRepository.searchClients("mohcine");
        // then
        assertThat(clients).isNotNull();
    }

    @Test
    void shouldFindClientByID(){
        // given
        Client client = ClientUtils.createClient(1L);
        clientRepository.save(client);
        // when
        Client result = clientRepository.findById(1L).orElse(null);
        // then
        assertThat(result).isNotNull();
    }
}