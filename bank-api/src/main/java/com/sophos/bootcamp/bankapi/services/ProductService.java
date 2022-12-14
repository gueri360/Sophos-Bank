package com.sophos.bootcamp.bankapi.services;

import com.sophos.bootcamp.bankapi.entities.Product;
import com.sophos.bootcamp.bankapi.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product createProduct (Product product) throws NotFoundException;

    List<Product> getAllProducts();

    Optional<Product> getProductById(Long id);

    Product modifyProduct (Product product);
}
