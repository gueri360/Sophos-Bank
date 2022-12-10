package com.sophos.bootcamp.bankapi.services.impl;

import com.sophos.bootcamp.bankapi.entities.Client;
import com.sophos.bootcamp.bankapi.repositories.ClientRepository;
import com.sophos.bootcamp.bankapi.services.ClientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImplement implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImplement(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client createClient(Client client) {
        Client clientSaved = clientRepository.save(client);
        return clientSaved;
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }
}
