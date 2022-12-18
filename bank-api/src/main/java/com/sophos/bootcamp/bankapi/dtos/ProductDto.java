package com.sophos.bootcamp.bankapi.dtos;

import com.sophos.bootcamp.bankapi.entities.Client;
import com.sophos.bootcamp.bankapi.entities.Product;
import com.sophos.bootcamp.bankapi.entities.enums.AccountStatus;
import com.sophos.bootcamp.bankapi.entities.enums.AccountType;
import com.sophos.bootcamp.bankapi.exceptions.BadRequestException;
import lombok.Data;

import java.util.Date;

@Data
public class ProductDto {

    private Long id;

    private Long accountCreator;

    private String accountType;

    private String accountNumber;

    private String accountStatus;

    private Double balance;

    private Double availableBalance;

    private Boolean gmfExempt;

    private Date creationDate;

    private Date modificationDate;

    private String modificationUser;

    public Product mapToDomain (){
        Product product = new Product();
        product.setAccountStatus(getAccountStatus(accountStatus));
        Client clientCreator = new Client();
        clientCreator.setId(accountCreator);
        product.setAccountCreator(clientCreator);
        product.setAccountType(getAccountType(accountType));
        product.setGmfExempt(gmfExempt);
        product.setModificationUser("admin");
        return product;
    }

    private AccountStatus getAccountStatus (String accountStatus){
        try {
            return AccountStatus.valueOf(accountStatus);
        }
        catch (Exception e){
            throw new BadRequestException("Account Status Not Supported");
        }
    }

    private AccountType getAccountType (String accountType){
        try {
            return AccountType.valueOf(accountType);
        }
        catch (Exception e){
            throw new BadRequestException("Account Type Not Supported");
        }
    }

}