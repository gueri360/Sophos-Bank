package com.sophos.bootcamp.bankapi.services;

import com.sophos.bootcamp.bankapi.entities.Client;
import com.sophos.bootcamp.bankapi.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ClientService {


    Optional<Client> findClientById (Long id);

    Client createClient(Client client);

    List<Client> getAllClients();

    Client modifyClient(Client client);

    Boolean deleteClientById(Long id);

}
