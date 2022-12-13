package com.sophos.bootcamp.bankapi.dtos;

import com.sophos.bootcamp.bankapi.entities.Client;
import com.sophos.bootcamp.bankapi.entities.Product;
import com.sophos.bootcamp.bankapi.entities.enums.AccountStatus;
import com.sophos.bootcamp.bankapi.entities.enums.AccountType;
import lombok.Data;

@Data
public class UpdateProductDto {

    private Long id;

    private String accountStatus;

    public Product mapToDomain (){
        Product product = new Product();
        product.setId(id);
        product.setAccountStatus(AccountStatus.valueOf(accountStatus));
        return product;
    }

}
