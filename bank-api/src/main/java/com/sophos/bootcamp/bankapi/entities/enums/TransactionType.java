package com.sophos.bootcamp.bankapi.entities.enums;

public enum TransactionType {
    DEPOSIT("DEPOSIT"), WITHDRAWAL("WITHDRAWAL"), TRANSFER("TRANSFER");

    private String status;

    TransactionType(String status){
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
