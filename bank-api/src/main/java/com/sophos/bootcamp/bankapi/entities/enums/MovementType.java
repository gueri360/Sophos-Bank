package com.sophos.bootcamp.bankapi.entities.enums;

public enum MovementType {
    CREDIT("CREDIT"), DEBIT("DEBIT"), WITHDRAWAL("WITHDRAWAL"), DEPOSIT("DEPOSIT");

    private String status;

    MovementType(String status){
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
