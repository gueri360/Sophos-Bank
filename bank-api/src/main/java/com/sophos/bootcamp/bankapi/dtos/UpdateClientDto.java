package com.sophos.bootcamp.bankapi.dtos;

import com.sophos.bootcamp.bankapi.entities.Client;
import lombok.Data;

import java.util.Date;

//TODO create this DTO
@Data
public class UpdateClientDto {

    private Long id;

    private String names;

    private String lastNames;

    private String emailAddress;

    private Date modificationDate;

    public Client mapToDto(){
        Client client = new Client();
        client.setId(id);
        client.setNames(names);
        client.setLastNames(lastNames);
        client.setEmailAddress(emailAddress);
        client.setModificationDate(new Date());
        return client;
    }
}
