package com.sophos.bootcamp.bankapi.entities.converters;

import com.sophos.bootcamp.bankapi.entities.enums.TransactionType;

import javax.persistence.AttributeConverter;

public class TransactionTypeConverter implements AttributeConverter <TransactionType, String> {


    @Override
    public String convertToDatabaseColumn(TransactionType transactionType) {
        return transactionType.getStatus();
    }

    @Override
    public TransactionType convertToEntityAttribute(String s) {
        return TransactionType.valueOf(s);
    }
}
