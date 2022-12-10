package com.sophos.bootcamp.bankapi.controllers;

import com.sophos.bootcamp.bankapi.entities.Client;
import com.sophos.bootcamp.bankapi.services.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client){
        Client clientCreated = clientService.createClient(client);
        return new ResponseEntity<>(clientCreated, HttpStatus.OK);
    }

}
