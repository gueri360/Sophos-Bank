package com.sophos.bootcamp.bankapi.exceptions;

import lombok.Data;

@Data
public class Error {

    private String message;

    public Error(String message) {
        this.message = message;
    }
}
