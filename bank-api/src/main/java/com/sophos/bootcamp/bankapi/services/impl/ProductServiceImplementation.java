package com.sophos.bootcamp.bankapi.services.impl;

import com.sophos.bootcamp.bankapi.entities.Product;
import com.sophos.bootcamp.bankapi.repositories.ProductRepository;
import com.sophos.bootcamp.bankapi.services.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImplementation implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImplementation(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(Product product) {
        Product createdProduct = productRepository.save(product);
        return createdProduct;

    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Boolean deleteProductById(Long id) {
        return getProductById(id).map(product -> {
            productRepository.deleteById(id);
            return true;
        }).orElse(false);

    }

    @Override
    public Product modifyProduct(Product product) {
        Optional<Product> verifyProductIdBeforeModify = productRepository.findById(product.getId());
        if (verifyProductIdBeforeModify.isPresent()) {
            Product modifiedProduct = productRepository.save(product);
            return modifiedProduct;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
