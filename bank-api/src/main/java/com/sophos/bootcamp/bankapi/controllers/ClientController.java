package com.sophos.bootcamp.bankapi.controllers;

import com.sophos.bootcamp.bankapi.dtos.UpdateClientDto;
import com.sophos.bootcamp.bankapi.entities.Client;
import com.sophos.bootcamp.bankapi.entities.Product;
import com.sophos.bootcamp.bankapi.exceptions.BadRequestException;
import com.sophos.bootcamp.bankapi.services.ClientService;
import com.sophos.bootcamp.bankapi.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;
    private final ProductService productService;

    public ClientController(ClientService clientService, ProductService productService) {
        this.clientService = clientService;
        this.productService = productService;
    }

    @PostMapping("/create")
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

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Client>> getClientById(@PathVariable Long id){
        Optional<Client> clientById = clientService.findClientById(id);
        return new ResponseEntity<>(clientById, HttpStatus.OK);
    }

    @GetMapping("/{id}/product")
    public ResponseEntity<List<Product>> getProductByClientId(@PathVariable Long id) {
        List<Product> allProductsByClientId = productService.getAllProductsByClientId(id);
        return new ResponseEntity<>(allProductsByClientId, HttpStatus.OK);
    }


    @PutMapping("/modify")
    public ResponseEntity<Client> modifyClient(@RequestBody UpdateClientDto client) {
        if (client.getId() == null) {
            throw new BadRequestException("Please, do not provide an ID number, this has already been automatically created by the system");
        }
        Client modifiedClient = clientService.modifyClient(client.mapToDto());
        return new ResponseEntity<>(modifiedClient, HttpStatus.OK);
    }

    //TODO soft delete
    @DeleteMapping("/{id}/delete")
    public ResponseEntity deleteClientById(@PathVariable("id") Long id){
        if (clientService.deleteClientById(id)){
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }


}
