package com.sophos.bootcamp.bankapi.entities;

import com.sophos.bootcamp.bankapi.entities.converters.AccountStatusConverter;
import com.sophos.bootcamp.bankapi.entities.enums.AccountStatus;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_creator", referencedColumnName = "client_id")
    private Client accountCreator;


    private String accountType;


    private String accountNumber;


    @Convert(converter = AccountStatusConverter.class)
    private AccountStatus accountStatus;


    private Double balance;


    private Double availableBalance;


    private Boolean gmfExempt;


    private Date creationDate;


    private Date modificationDate;


    private String modificationUser;


    public Product() {
    }

}
