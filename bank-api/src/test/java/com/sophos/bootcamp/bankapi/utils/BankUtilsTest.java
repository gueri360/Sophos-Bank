package com.sophos.bootcamp.bankapi.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankUtilsTest {

    @Test
    void validateEmailAddress() {
        assertTrue(BankUtils.validateEmailAddress("acm1pt@gmail.com"));
        assertTrue(BankUtils.validateEmailAddress("acm-1.p_t@gmail.com"));
        assertFalse(BankUtils.validateEmailAddress("ACM2PT@gmail.com"));
        assertFalse(BankUtils.validateEmailAddress("acm3ptgmail.com"));
        assertFalse(BankUtils.validateEmailAddress("acm4pt@gmailcom"));
        assertFalse(BankUtils.validateEmailAddress("acm5pt@gmail."));

    }
}