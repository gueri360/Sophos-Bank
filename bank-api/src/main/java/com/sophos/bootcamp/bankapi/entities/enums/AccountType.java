package com.sophos.bootcamp.bankapi.entities.enums;

public enum AccountType {
    SAVINGS("SAVINGS"), CHECKING("CHECKING");

    private String status;

    AccountType(String status){
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
