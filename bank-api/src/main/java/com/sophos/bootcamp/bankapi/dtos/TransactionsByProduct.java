package com.sophos.bootcamp.bankapi.dtos;

import com.sophos.bootcamp.bankapi.entities.Transaction;
import com.sophos.bootcamp.bankapi.utils.BankUtils;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

import static com.sophos.bootcamp.bankapi.entities.enums.MovementType.*;
import static com.sophos.bootcamp.bankapi.entities.enums.TransactionType.*;

@Data
public class TransactionsByProduct {

    public static List<TransactionResultDto> mapToDto(List<Transaction> transactions, Long productId) {
        return transactions.stream()
                .map(tx -> {
                    TransactionResultDto transactionDto = new TransactionResultDto();
                    transactionDto.setTransactionDate(tx.getModificationDate());
                    transactionDto.setTransactionType(tx.getTransactionType().getStatus());
                    transactionDto.setDescription(tx.getDescription());
                    transactionDto.setTransactionAmount(tx.getTransactionAmount());
                    transactionDto.setBalance(tx.getSenderBalance());
                    if (TRANSFER.equals(tx.getTransactionType())) {
                        if (tx.getRecipient().equals(productId)) {
                            transactionDto.setMovementType(CREDIT.getStatus());
                            transactionDto.setBalance(tx.getRecipientBalance());
                            transactionDto.setAvailableBalance(BankUtils.getAvailableBalance(tx.getRecipientBalance(),
                                    tx.getRecipient().getGmfExempt(), tx.getRecipient().getAccountType()));
                        } else {
                            transactionDto.setMovementType(DEBIT.getStatus());
                            transactionDto.setBalance(tx.getSenderBalance());
                            transactionDto.setAvailableBalance(BankUtils.getAvailableBalance(tx.getSenderBalance(),
                                    tx.getSender().getGmfExempt(), tx.getSender().getAccountType()));
                        }
                    } else if (DEPOSIT.equals(tx.getTransactionType())){
                        transactionDto.setMovementType(CREDIT.getStatus());
                        transactionDto.setBalance(tx.getRecipientBalance());
                        transactionDto.setAvailableBalance(BankUtils.getAvailableBalance(tx.getRecipientBalance(),
                                tx.getRecipient().getGmfExempt(), tx.getRecipient().getAccountType()));

                    } else if (WITHDRAWAL.equals(tx.getTransactionType())){
                        transactionDto.setMovementType(DEBIT.getStatus());
                        transactionDto.setBalance(tx.getSenderBalance());
                        transactionDto.setAvailableBalance(BankUtils.getAvailableBalance(tx.getSenderBalance(),
                                tx.getSender().getGmfExempt(), tx.getSender().getAccountType()));
                    }
                    return transactionDto;
                })
                .collect(Collectors.toList());
    }
}
