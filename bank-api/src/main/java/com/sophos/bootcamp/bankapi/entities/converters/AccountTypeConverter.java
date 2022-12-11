package com.sophos.bootcamp.bankapi.entities.converters;

import com.sophos.bootcamp.bankapi.entities.enums.AccountType;

import javax.persistence.AttributeConverter;

public class AccountTypeConverter implements AttributeConverter <AccountType, String> {


    @Override
    public String convertToDatabaseColumn(AccountType accountType) {
        return accountType.getStatus();
    }

    @Override
    public AccountType convertToEntityAttribute(String s) {
        return AccountType.valueOf(s);
    }
}
