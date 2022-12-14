package com.sophos.bootcamp.bankapi.services.impl;

import com.sophos.bootcamp.bankapi.entities.Product;
import com.sophos.bootcamp.bankapi.entities.Transaction;
import com.sophos.bootcamp.bankapi.entities.enums.MovementType;
import com.sophos.bootcamp.bankapi.exceptions.BadRequestException;
import com.sophos.bootcamp.bankapi.exceptions.NotFoundException;
import com.sophos.bootcamp.bankapi.repositories.ProductRepository;
import com.sophos.bootcamp.bankapi.repositories.TransactionRepository;
import com.sophos.bootcamp.bankapi.services.TransactionService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.sophos.bootcamp.bankapi.entities.enums.AccountStatus.INACTIVE;
import static com.sophos.bootcamp.bankapi.entities.enums.MovementType.DEBIT;
import static com.sophos.bootcamp.bankapi.entities.enums.MovementType.CREDIT;


@Service
public class TransactionServiceImplementation implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;

    public TransactionServiceImplementation(TransactionRepository transactionRepository,
                                            ProductRepository productRepository) {
        this.transactionRepository = transactionRepository;
        this.productRepository = productRepository;
    }

    //TODO La cuenta de ahorros no puede tener un saldo menor a $0 (cero).
    //TODO La cuenta corriente puede sobregirarse hasta un máximo de $3.000.000.
    @Override
    public Optional<Transaction> findTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    @Override
    public Transaction createTransaction(Transaction transaction) {
        Product productSender = productRepository.findById(transaction.getSender().getId())
                .orElseThrow(() -> new NotFoundException("This product does not exist"));
        Product productRecipient = productRepository.findById(transaction.getRecipient().getId())
                .orElseThrow(() -> new NotFoundException("This product does not exist"));
        transaction.setSender(productSender);
        transaction.setRecipient(productRecipient);
        transaction.setModificationDate(new Date());
        if (findMovementType(DEBIT) || findMovementType(CREDIT)) {
            Double transactionAmount = transaction.getTransactionAmount();
            Double senderBalanceModified = productSender.getBalance() - transactionAmount;
            Double recipientBalanceModified = productRecipient.getBalance() + transactionAmount;
            productSender.setBalance(senderBalanceModified);
            productRecipient.setBalance(recipientBalanceModified);
        }
        if (INACTIVE.equals(productSender.getAccountStatus())){
            throw new BadRequestException("This account can not complete debit transactions");
        }
        Transaction createdTransaction = transactionRepository.save(transaction);

        return createdTransaction;
    }

    @Override
    public List<Transaction> listOfTransactions() {
        return transactionRepository.findAll();
    }

    private Boolean findMovementType (MovementType movementType){
        if (movementType.equals(DEBIT)){
            return true;
        } else return false;
    }

}
