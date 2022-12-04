package com.sophos.bootcamp.bankapi.repositories;

import com.sophos.bootcamp.bankapi.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
