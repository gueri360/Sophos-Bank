package com.sophos.bootcamp.bankapi.entities.converters;

import com.sophos.bootcamp.bankapi.entities.enums.AccountStatus;

import javax.persistence.AttributeConverter;

public class AccountStatusConverter implements AttributeConverter<AccountStatus,String> {


    @Override
    public String convertToDatabaseColumn(AccountStatus accountStatus) {
        return accountStatus.getStatus();
    }

    @Override
    public AccountStatus convertToEntityAttribute(String s) {
        return AccountStatus.valueOf(s);
    }
}
