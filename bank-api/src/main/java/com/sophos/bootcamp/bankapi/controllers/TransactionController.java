package com.sophos.bootcamp.bankapi.controllers;


import com.sophos.bootcamp.bankapi.dtos.TransactionDto;
import com.sophos.bootcamp.bankapi.entities.Transaction;
import com.sophos.bootcamp.bankapi.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@CrossOrigin(origins = "localhost:4200")
public class TransactionController {

    public final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction (@RequestBody TransactionDto transactionDto){
        Transaction transactionCreated = transactionService.createTransaction(transactionDto.mapTransferToDomain());
        return new ResponseEntity<>(transactionCreated, HttpStatus.OK);
    }
}
