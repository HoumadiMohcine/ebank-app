package com.ebankapp.repositories;

import com.ebankapp.dto.ClientDTO;
import com.ebankapp.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
public interface ClientRepository extends JpaRepository<Client,Long> {
        @Query("select c from Client c where c.name like :kw")
        List<Client> searchClients(@Param("kw") String keyword);
}

