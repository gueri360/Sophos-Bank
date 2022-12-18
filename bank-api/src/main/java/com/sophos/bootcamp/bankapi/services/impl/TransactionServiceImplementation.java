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
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.sophos.bootcamp.bankapi.entities.enums.AccountStatus.CANCELLED;
import static com.sophos.bootcamp.bankapi.entities.enums.AccountStatus.INACTIVE;
import static com.sophos.bootcamp.bankapi.entities.enums.AccountType.CHECKING;
import static com.sophos.bootcamp.bankapi.entities.enums.AccountType.SAVINGS;
import static com.sophos.bootcamp.bankapi.entities.enums.MovementType.CREDIT;
import static com.sophos.bootcamp.bankapi.entities.enums.MovementType.DEBIT;


@Service
public class TransactionServiceImplementation implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;

    public TransactionServiceImplementation(TransactionRepository transactionRepository,
                                            ProductRepository productRepository) {
        this.transactionRepository = transactionRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Optional<Transaction> findTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    @Override
    @Transactional //Avoids saving transaction if something fails.
    public Transaction createTransaction(Transaction transaction) {
        Product productSender = productRepository.findById(transaction.getSender().getId())
                .orElseThrow(() -> new NotFoundException("This product does not exist"));
        Product productRecipient = productRepository.findById(transaction.getRecipient().getId())
                .orElseThrow(() -> new NotFoundException("This product does not exist"));
        transaction.setModificationDate(new Date());
        if (findMovementType(DEBIT) || findMovementType(CREDIT)) {
            Double transactionAmount = transaction.getTransactionAmount();
            if(productSender.getGmfExempt() == false){
                transactionAmount = getGmfCalculator(transactionAmount) + transactionAmount;
            }
            Double senderBalanceModified = productSender.getBalance() - transactionAmount;
            Double recipientBalanceModified = productRecipient.getBalance() + transactionAmount;
            productSender.setBalance(senderBalanceModified);
            productRecipient.setBalance(recipientBalanceModified);
            if (SAVINGS.equals(productSender.getAccountType()) && (senderBalanceModified < 0)) {
                throw new BadRequestException("Insufficient funds");
            }

            if (CHECKING.equals(productSender.getAccountType()) && (senderBalanceModified < -3000000)) {
                throw new BadRequestException("Insufficient funds");
            }

            if (INACTIVE.equals(productSender.getAccountStatus()) || CANCELLED.equals(productSender.getAccountStatus())) {
                throw new BadRequestException("This account can not complete debit transactions");
            }

            if (CANCELLED.equals(productRecipient.getAccountStatus())) {
                throw new BadRequestException("This account can not complete credit transactions");
            }
            if (SAVINGS.equals(productSender.getAccountType()) || CHECKING.equals(productSender.getAccountType())) {
                productSender.setAvailableBalance(productSender.getBalance());
            }

            if (SAVINGS.equals(productRecipient.getAccountType()) || CHECKING.equals(productRecipient.getAccountType())) {
                productRecipient.setAvailableBalance(productRecipient.getBalance());
            }
        }

        transaction.setSender(productSender);
        transaction.setRecipient(productRecipient);
        productRepository.save(transaction.getSender());
        productRepository.save(transaction.getRecipient());
        Transaction createdTransaction = transactionRepository.save(transaction);
        return createdTransaction;
    }

    @Override
    public List<Transaction> listOfTransactions() {
        return transactionRepository.findAll();
    }

    private Boolean findMovementType(MovementType movementType) {
        if (movementType.equals(DEBIT)) {
            return true;
        } else return false;
    }

    private Double getGmfCalculator (Double transactionAmount){
        double gmf = transactionAmount / 1000 * 4;
        return gmf;
    }

}
