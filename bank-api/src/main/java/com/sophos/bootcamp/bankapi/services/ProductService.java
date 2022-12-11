package com.sophos.bootcamp.bankapi.services;

import com.sophos.bootcamp.bankapi.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product createProduct (Product product);

    List<Product> getAllProducts();

    Optional<Product> getProductById(Long id);

    Boolean deleteProductById (Long id);

    Product modifyProduct (Product product);
}
