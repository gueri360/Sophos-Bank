package com.sophos.bootcamp.bankapi.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "sender_id",referencedColumnName = "product_id")
    private Product sender;
    @ManyToOne
    @JoinColumn(name = "recipient_id",referencedColumnName = "product_id")
    private Product recipient;
    private Date modificationDate;
    private String transactionType;
    private String description;
    private Double transactionAmount;
    private String movementType;
    private Double balance;
    private Double availableBalance;

    public Transaction() {
    }

}
