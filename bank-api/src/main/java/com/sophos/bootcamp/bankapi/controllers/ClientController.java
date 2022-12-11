package com.sophos.bootcamp.bankapi.controllers;

import com.sophos.bootcamp.bankapi.entities.Client;
import com.sophos.bootcamp.bankapi.services.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        if (client.getId() != null){
            throw new IllegalArgumentException("Do not provide ID number, this will be automatically created by the system");
        }
        Client clientCreated = clientService.createClient(client);
        return new ResponseEntity<>(clientCreated, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        return new ResponseEntity<>(clientService.getAllClients(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Client> modifyClient(@RequestBody Client client) {
        if (client.getId() == null) {
            throw new IllegalArgumentException("Please, do not provide an ID number, this has already been automatically created by the system");
        }
        Client modifiedClient = clientService.modifyClient(client);
        return new ResponseEntity<>(modifiedClient, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteClientById(@PathVariable("id") Long id){
        if (clientService.deleteClientById(id)){
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

}
