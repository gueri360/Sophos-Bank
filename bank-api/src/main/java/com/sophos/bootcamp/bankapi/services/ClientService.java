package com.sophos.bootcamp.bankapi.services;

import com.sophos.bootcamp.bankapi.entities.Client;

import java.util.List;

public interface ClientService {

    Client createClient(Client client);

    List<Client> getAllClients();

}
