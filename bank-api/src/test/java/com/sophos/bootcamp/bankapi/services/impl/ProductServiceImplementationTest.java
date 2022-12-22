package com.sophos.bootcamp.bankapi.services.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceImplementationTest {

    @Test
    void getAccountNumber() {

        ProductServiceImplementation productService = new ProductServiceImplementation(null, null);
        String accountNumber = productService.getAccountNumber("11", "1");
        Assertions.assertEquals("1100000001", accountNumber);

        String accountNumber2 = productService.getAccountNumber("11", "35");
        Assertions.assertEquals("1100000035", accountNumber2);

    }
}