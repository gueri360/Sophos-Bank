package com.sophos.bootcamp.bankapi.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class TransactionResultDto {

    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date transactionDate;

    private String transactionType;

    private String description;

    private Double transactionAmount;

    private String movementType;

    private Double balance;

    private Double availableBalance;

}
