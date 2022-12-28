package com.sophos.bootcamp.bankapi.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;


@Entity
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long id;
//TODO handle id_type with an enum: CC, CE, NIT, and Passport
    @Column(name = "id_type")
    private String idType;

    @Column(name = "id_number")
    private String idNumber;

    @Column(name = "names")
    private String names;

    @Column(name = "last_names")
    private String lastNames;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "client_creator")
    private String clientCreator;

    @Column(name = "modification_date")
    private Date modificationDate;

    @Column(name = "modification_user")
    private String modificationUser;

    public Client() {
        this.clientCreator = "Admin";
    }



}
