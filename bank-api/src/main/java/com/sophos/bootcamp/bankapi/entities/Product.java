package com.sophos.bootcamp.bankapi.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sophos.bootcamp.bankapi.entities.converters.AccountStatusConverter;
import com.sophos.bootcamp.bankapi.entities.converters.AccountTypeConverter;
import com.sophos.bootcamp.bankapi.entities.enums.AccountStatus;
import com.sophos.bootcamp.bankapi.entities.enums.AccountType;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
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

    @Convert(converter = AccountTypeConverter.class )
    @Column(name = "account_type")
    private AccountType accountType;

    @Column(name = "account_number")
    private String accountNumber;

    @Convert(converter = AccountStatusConverter.class)
    @Column(name = "account_status")
    private AccountStatus accountStatus;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "available_balance")
    private Double availableBalance;

    @Column(name = "gmf_exempt")
    private Boolean gmfExempt;

    @Column(name = "creation_date")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date creationDate;

    @Column(name = "modification_date")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date modificationDate;

    @Column(name = "modification_user")
    private String modificationUser;


    public Product() {
    }

}
