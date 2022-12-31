package com.sophos.bootcamp.bankapi.dtos;

import com.sophos.bootcamp.bankapi.entities.Product;
import com.sophos.bootcamp.bankapi.entities.Transaction;
import com.sophos.bootcamp.bankapi.entities.enums.MovementType;
import com.sophos.bootcamp.bankapi.entities.enums.TransactionType;
import com.sophos.bootcamp.bankapi.exceptions.BadRequestException;
import lombok.Data;

import static com.sophos.bootcamp.bankapi.entities.enums.TransactionType.TRANSFER;


@Data
public class TransactionDto {

    private Long sender;

    private Long recipient;

    private String transactionType;

    private String description;

    private Double transactionAmount;

    public Transaction mapTransferToDomain(){
        Transaction transaction = new Transaction();
        TransactionType transactionType = getTransactionType(this.transactionType);
        transaction.setTransactionType(transactionType);
        Product productCreatorRecipient = new Product();
        productCreatorRecipient.setId(recipient);
        transaction.setRecipient(productCreatorRecipient);
        Product productCreatorSender = new Product();
        productCreatorSender.setId(sender);
        transaction.setSender(productCreatorSender);
        transaction.setDescription(description);
        transaction.setTransactionAmount(transactionAmount);
        return transaction;
    }

    private TransactionType getTransactionType (String transactionType){
        try {
            return TransactionType.valueOf(transactionType);
        }
        catch (Exception e){
            throw new BadRequestException("Transaction Type Not Supported");
        }
    }

    private MovementType getMovementType (String movementType){
        try {
            return MovementType.valueOf(movementType);
        }
        catch (Exception e){
            throw new BadRequestException("Movement Type Not Supported");
        }
    }
}
