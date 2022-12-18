package com.sophos.bootcamp.bankapi.entities;

import com.sophos.bootcamp.bankapi.entities.converters.MovementTypeConverter;
import com.sophos.bootcamp.bankapi.entities.converters.TransactionTypeConverter;
import com.sophos.bootcamp.bankapi.entities.enums.MovementType;
import com.sophos.bootcamp.bankapi.entities.enums.TransactionType;
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


    @Column(name = "modification_date")
    private Date modificationDate;

    @Convert ( converter = TransactionTypeConverter.class)
    @Column(name = "transaction_type")
    private TransactionType transactionType;

    @Column(name = "description")
    private String description;

    @Column(name = "transaction_amount")
    private Double transactionAmount;

    @Convert ( converter = MovementTypeConverter.class)
    @Column(name = "movement_type")
    private MovementType movementType;

    public Transaction() {
    }
}
