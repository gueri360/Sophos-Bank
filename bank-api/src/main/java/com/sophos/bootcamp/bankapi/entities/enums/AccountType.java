package com.sophos.bootcamp.bankapi.entities.enums;

public enum AccountType {
    DEBIT("DEBIT"), CREDIT("CREDIT");

    private String status;

    AccountType(String status){
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
