package com.sophos.bootcamp.bankapi.repositories;

import com.sophos.bootcamp.bankapi.entities.Client;
import com.sophos.bootcamp.bankapi.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
//    List<Product> findByAccountCreator(Client client);
}
