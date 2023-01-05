package com.sophos.bootcamp.bankapi.repositories;

import com.sophos.bootcamp.bankapi.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByIdNumber(String idNumber);

    @Query(value = "SELECT * FROM client c WHERE c.is_deleted = false", nativeQuery = true)
    List<Client> findAll();

}
