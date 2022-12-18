package com.sophos.bootcamp.bankapi.controllers;


import com.sophos.bootcamp.bankapi.dtos.ProductDto;
import com.sophos.bootcamp.bankapi.dtos.UpdateProductDto;
import com.sophos.bootcamp.bankapi.entities.Product;
import com.sophos.bootcamp.bankapi.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    public final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductDto productDto) {
//        if (productDto.getId() != null) {
//            throw new IllegalArgumentException("Do not provide an ID as it is automatically created by the system");
//        } else {
            Product productCreated = productService.createProduct(productDto.mapToDomain());
            return new ResponseEntity<>(productCreated, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Product> modifyProduct(@RequestBody UpdateProductDto product) {
        if (product.getId() == null) {
            throw new IllegalArgumentException("This product isn't listed in our system");
        } else {
            Product modifiedProduct = productService.modifyProduct(product.mapToDomain());
            return new ResponseEntity<>(modifiedProduct, HttpStatus.OK);
        }
    }
}
