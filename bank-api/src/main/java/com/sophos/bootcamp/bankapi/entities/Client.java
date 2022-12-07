package com.sophos.bootcamp.bankapi.entities;

import java.util.Date;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.parsing.ConstructorArgumentEntry;

import javax.persistence.*;


@Entity
@Data
public class Client {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "client_id")
	private Long id;
	private String idType;
	private String idNumber;
	private String names;
	private String lastNames;
	private String emailAddress;
	private Date dateOfBirth;
	private Date creationDate;
	private String clientCreator;
	private Date modificationDate;
	private String modificationUser;

	public Client() {
		this.clientCreator = "Admin";
	}


}
