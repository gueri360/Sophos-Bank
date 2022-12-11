package com.sophos.bootcamp.bankapi.entities.enums;

public enum MovementType {
    DEPOSIT("DEPOSIT"),WITHDRAWAL("WITHDRAWAL"),TRANSFER("TRANSFER");

    private String status;

    MovementType(String status){
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
