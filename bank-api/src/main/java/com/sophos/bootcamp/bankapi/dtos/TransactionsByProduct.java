package com.sophos.bootcamp.bankapi.dtos;

import com.sophos.bootcamp.bankapi.entities.Transaction;
import com.sophos.bootcamp.bankapi.utils.BankUtils;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

import static com.sophos.bootcamp.bankapi.entities.enums.MovementType.CREDIT;
import static com.sophos.bootcamp.bankapi.entities.enums.MovementType.DEBIT;
import static com.sophos.bootcamp.bankapi.entities.enums.TransactionType.TRANSFER;

@Data
public class TransactionsByProduct {

    public List<TransactionDto> transactionDtos;

    public List<TransactionResultDto> mapToDto(List<Transaction> transactions, Long productId) {
        return transactions.stream()
                .map(tx -> {
                    TransactionResultDto transactionDto = new TransactionResultDto();
                    transactionDto.setTransactionDate(tx.getModificationDate());
                    transactionDto.setTransactionType(tx.getTransactionType().getStatus());
                    transactionDto.setDescription(tx.getDescription());
                    transactionDto.setTransactionAmount(tx.getTransactionAmount());
                    transactionDto.setMovementType(tx.getMovementType().getStatus());
                    transactionDto.setBalance(tx.getSenderBalance());
                    if (TRANSFER.equals(tx.getTransactionType())) {
                        if (tx.getRecipient().equals(productId)) {
                            transactionDto.setMovementType(CREDIT.getStatus());
                            transactionDto.setBalance(tx.getRecipientBalance());
                            transactionDto.setAvailableBalance(BankUtils.getAvailableBalance(tx.getRecipientBalance(), tx.getRecipient().getGmfExempt()));
                        } else {
                            transactionDto.setMovementType(DEBIT.getStatus());
                            transactionDto.setBalance(tx.getSenderBalance());
                            transactionDto.setAvailableBalance(BankUtils.getAvailableBalance(tx.getSenderBalance(), tx.getSender().getGmfExempt()));
                        }
                    }
                    return transactionDto;
                })
                .collect(Collectors.toList());
    }
}
