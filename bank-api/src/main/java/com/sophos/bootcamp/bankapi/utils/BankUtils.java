package com.sophos.bootcamp.bankapi.utils;

public class BankUtils {

    public static Double getGmfCalculator(Double transactionAmount, Boolean gmfExempt) {
        if(gmfExempt == false ){
            double gmf = transactionAmount / 1000 * 4;
            return gmf + transactionAmount;
        } else return transactionAmount;
    }

    public static Double getAvailableBalance(Double transactionAmount, Boolean gmfExempt) {
        if(gmfExempt == false ){
            double gmf = transactionAmount / 1000 * 4;
            return gmf - transactionAmount;
        } else return transactionAmount;
    }


}
