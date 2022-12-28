package com.sophos.bootcamp.bankapi.controllers;


import com.sophos.bootcamp.bankapi.dtos.ProductDto;
import com.sophos.bootcamp.bankapi.dtos.UpdateProductDto;
import com.sophos.bootcamp.bankapi.entities.Product;
import com.sophos.bootcamp.bankapi.entities.Transaction;
import com.sophos.bootcamp.bankapi.exceptions.BadRequestException;
import com.sophos.bootcamp.bankapi.services.ProductService;
import com.sophos.bootcamp.bankapi.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    public final ProductService productService;
    public final TransactionService transactionService;

    public ProductController(ProductService productService, TransactionService transactionService) {
        this.productService = productService;
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductDto productDto) {
        if (productDto.getId() != null) {
            throw new IllegalArgumentException("Do not provide an ID as it is automatically created by the system");
        } else {
            Product productCreated = productService.createProduct(productDto.mapToDomain());
            return new ResponseEntity<>(productCreated, HttpStatus.OK);
        }
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<Transaction>> getTransactionByProductId(@PathVariable Long id) {
        List<Transaction> allTransactionsByProductId = transactionService.listOfTransactions(id);
        return new ResponseEntity<>(allTransactionsByProductId, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Product> modifyProduct(@RequestBody UpdateProductDto product) {
        if (product.getId() == null) {
            throw new BadRequestException("This product isn't listed in our system");
        } else {
            Product modifiedProduct = productService.modifyProduct(product.mapToDomain());
            return new ResponseEntity<>(modifiedProduct, HttpStatus.OK);
        }
    }
}
