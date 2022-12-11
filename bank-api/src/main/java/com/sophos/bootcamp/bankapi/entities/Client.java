package com.sophos.bootcamp.bankapi.entities;

import java.time.LocalDate;
import java.util.Date;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.parsing.ConstructorArgumentEntry;

import javax.persistence.*;


@Entity
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long id;

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

//    I will create this as one of the columns in the DB
//    @Column
//    (name = "creation_date")
//    private LocalDate creationDate;

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
