package com.sophos.bootcamp.bankapi.services.impl;

import com.sophos.bootcamp.bankapi.entities.Transaction;
import com.sophos.bootcamp.bankapi.repositories.TransactionRepository;
import com.sophos.bootcamp.bankapi.services.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImplementation implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImplementation(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


    @Override
    public Optional<Transaction> findTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    @Override
    public Transaction createTransaction(Transaction transaction) {
        Transaction createdTransaction = transactionRepository.save(transaction);
        return createdTransaction;
    }

    @Override
    public List<Transaction> listOfTransactions() {
        return transactionRepository.findAll();
    }
}
