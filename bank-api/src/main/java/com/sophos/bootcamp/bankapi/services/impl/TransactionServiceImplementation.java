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
import static com.sophos.bootcamp.bankapi.entities.enums.TransactionType.*;
import static com.sophos.bootcamp.bankapi.utils.BankUtils.getAvailableBalance;
import static com.sophos.bootcamp.bankapi.utils.BankUtils.getGmfCalculator;

//TODO update Available balance
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
        if (TRANSFER.equals(transaction.getTransactionType())) {
            return processTransferTransaction(transaction);
        } else if (WITHDRAWAL.equals(transaction.getTransactionType())) {
            return processWithdrawTransaction(transaction);
        } else if (DEPOSIT.equals(transaction.getTransactionType())) {
            return processDepositTransaction(transaction);
        } else throw new BadRequestException("Transaction not supported");
    }

    //TODO make the CREDIT movement type show for the recipient and DEBIT for the sender
    @Override
    public List<Transaction> listOfTransactions(Long id) {
        productRepository.findById(id).orElseThrow(() -> new NotFoundException("This account does not exist"));
        List<Transaction> transactions = transactionRepository.findAllBySenderIdOrRecipientId(id);
        return transactions;
    }

    private Transaction processTransferTransaction(Transaction transaction) {
        Product productSender = productRepository.findById(transaction.getSender().getId())
                .orElseThrow(() -> new NotFoundException("This product does not exist"));
        Product productRecipient = productRepository.findById(transaction.getRecipient().getId())
                .orElseThrow(() -> new NotFoundException("This product does not exist"));
        transaction.setModificationDate(new Date());
        Double transactionAmount = transaction.getTransactionAmount();

        transactionAmount = getGmfCalculator(transactionAmount, productSender.getGmfExempt());

        Double senderBalanceModified = productSender.getBalance() - transactionAmount;
        Double recipientBalanceModified = productRecipient.getBalance() + transactionAmount;
        productSender.setBalance(senderBalanceModified);
        productSender.setAvailableBalance(getAvailableBalance(senderBalanceModified, productSender.getGmfExempt(), productSender.getAccountType()));
        productRecipient.setBalance(recipientBalanceModified);
        productRecipient.setAvailableBalance(getAvailableBalance(recipientBalanceModified, productRecipient.getGmfExempt(), productRecipient.getAccountType()));
        transaction.setRecipientBalance(recipientBalanceModified);
        transaction.setSenderBalance(senderBalanceModified);

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
            productSender.setAvailableBalance(getAvailableBalance(productSender.getBalance(), productSender.getGmfExempt(), productSender.getAccountType()));
        }

        if (SAVINGS.equals(productRecipient.getAccountType()) || CHECKING.equals(productRecipient.getAccountType())) {
            productRecipient.setAvailableBalance(getAvailableBalance(productRecipient.getBalance(), productRecipient.getGmfExempt(), productRecipient.getAccountType()));
        }

        transaction.setMovementType(MovementType.DEBIT);
        transaction.setSender(productSender);
        transaction.setRecipient(productRecipient);
        productRepository.save(transaction.getSender());
        productRepository.save(transaction.getRecipient());
        Transaction createdTransaction = transactionRepository.save(transaction);
        return createdTransaction;
    }

    private Transaction processWithdrawTransaction(Transaction transaction) {
        Product productSender = productRepository.findById(transaction.getSender().getId())
                .orElseThrow(() -> new NotFoundException("This product does not exist"));
        transaction.setModificationDate(new Date());
        Double withdrawalAmount = transaction.getTransactionAmount();

        if (INACTIVE.equals(productSender.getAccountStatus())) {
            throw new BadRequestException("This account is inactive");
        }

        if (CANCELLED.equals(productSender.getAccountStatus())) {
            throw new BadRequestException("This account is cancelled");
        }

        withdrawalAmount = getGmfCalculator(withdrawalAmount, productSender.getGmfExempt());

        Double senderBalanceModified = productSender.getBalance() - withdrawalAmount;

        if (CHECKING.equals(productSender.getAccountType()) && (senderBalanceModified < -3000000)) {
            throw new BadRequestException("Insufficient funds");
        }
        if (SAVINGS.equals(productSender.getAccountType()) && (senderBalanceModified < 0)) {
            throw new BadRequestException("Insufficient funds");
        }

        productSender.setBalance(senderBalanceModified);
        productSender.setAvailableBalance(getAvailableBalance(senderBalanceModified, productSender.getGmfExempt(), productSender.getAccountType()));
        transaction.setSender(productSender);
        transaction.setRecipient(null);
        transaction.setMovementType(MovementType.DEBIT);
        transaction.setSenderBalance(senderBalanceModified);
        productRepository.save(transaction.getSender());
        Transaction createdTransaction = transactionRepository.save(transaction);
        return createdTransaction;
    }

    private Transaction processDepositTransaction(Transaction transaction) {
        Product productRecipient = productRepository.findById(transaction.getRecipient().getId())
                .orElseThrow(() -> new NotFoundException("This product does not exist"));
        transaction.setModificationDate(new Date());
        Double depositAmount = transaction.getTransactionAmount();
        if (CANCELLED.equals(productRecipient.getAccountStatus())) {
            throw new BadRequestException("This account is cancelled");
        }
        Double recipientBalanceModified = productRecipient.getBalance() + depositAmount;
        productRecipient.setBalance(recipientBalanceModified);
        productRecipient.setAvailableBalance(getAvailableBalance(recipientBalanceModified, productRecipient.getGmfExempt(), productRecipient.getAccountType()));
        transaction.setRecipient(productRecipient);
        transaction.setSender(null);
        transaction.setMovementType(MovementType.CREDIT);
        transaction.setRecipientBalance(recipientBalanceModified);
        productRepository.save(transaction.getRecipient());
        Transaction createdTransaction = transactionRepository.save(transaction);
        return createdTransaction;
    }

}
