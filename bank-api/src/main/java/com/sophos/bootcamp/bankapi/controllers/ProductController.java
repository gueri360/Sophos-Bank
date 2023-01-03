package com.sophos.bootcamp.bankapi.controllers;


import com.sophos.bootcamp.bankapi.dtos.*;
import com.sophos.bootcamp.bankapi.entities.Product;
import com.sophos.bootcamp.bankapi.entities.Transaction;
import com.sophos.bootcamp.bankapi.exceptions.BadRequestException;
import com.sophos.bootcamp.bankapi.services.ProductService;
import com.sophos.bootcamp.bankapi.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    public final ProductService productService;
    public final TransactionService transactionService;

    public ProductController(ProductService productService, TransactionService transactionService) {
        this.productService = productService;
        this.transactionService = transactionService;
    }

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody ProductDto productDto) {
        if (productDto.getId() != null) {
            throw new IllegalArgumentException("Do not provide an ID as it is automatically created by the system");
        } else {
            Product productCreated = productService.createProduct(productDto.mapToDomain());
            return new ResponseEntity<>(productCreated, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Product>> getProductById(@PathVariable Long id){
        Optional<Product> productById = productService.getProductById(id);
        return new ResponseEntity<>(productById, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<TransactionResultDto>> getTransactionByProductId(@PathVariable Long id) {
        List<Transaction> allTransactionsByProductId = transactionService.listOfTransactions(id);
        List<TransactionResultDto> filteredList = TransactionsByProduct.mapToDto(allTransactionsByProductId, id);
        return new ResponseEntity<>(filteredList, HttpStatus.OK);
    }

    @PutMapping("/modify")
    public ResponseEntity<Product> modifyProduct(@RequestBody UpdateProductDto product) {
        if (product.getId() == null) {
            throw new BadRequestException("This product isn't listed in our system");
        } else {
            Product modifiedProduct = productService.modifyProduct(product.mapToDomain());
            return new ResponseEntity<>(modifiedProduct, HttpStatus.OK);
        }
    }
}
