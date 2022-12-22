package com.sophos.bootcamp.bankapi.dtos;


import com.sophos.bootcamp.bankapi.entities.Product;
import com.sophos.bootcamp.bankapi.entities.enums.AccountStatus;
import com.sophos.bootcamp.bankapi.exceptions.BadRequestException;
import lombok.Data;

@Data
public class UpdateProductDto {

    private Long id;

    private String accountStatus;

    private Boolean gmfExempt;

    public Product mapToDomain (){
        Product product = new Product();
        product.setId(id);
        product.setAccountStatus(getAccountStatus(accountStatus));
        product.setGmfExempt(gmfExempt);
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

}
