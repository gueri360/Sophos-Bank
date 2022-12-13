package com.sophos.bootcamp.bankapi.services.impl;

import com.sophos.bootcamp.bankapi.entities.Client;
import com.sophos.bootcamp.bankapi.repositories.ClientRepository;
import com.sophos.bootcamp.bankapi.services.ClientService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImplementation implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImplementation(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Optional<Client> findClientById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public Client createClient(Client client) {
        if (isClientOver18Yo(client.getDateOfBirth())) {
            Client clientSaved = clientRepository.save(client);
            return clientSaved;
        } else throw new IllegalArgumentException("Client is underage");

    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client modifyClient(Client client) {
        Optional<Client> verifyId = clientRepository.findById(client.getId());
        if (verifyId.isPresent()) {
            Client clientModified = clientRepository.save(client);
            return clientModified;
        } else {
            throw new IllegalArgumentException("This Client is not registered in our system, please verify the id or create a Client");
        }
    }

    @Override
    public Boolean deleteClientById(Long id) {
        return findClientById(id).map(client -> {
            clientRepository.deleteById(id);
            return true;
        }).orElse(false);
    }

    public Boolean isClientOver18Yo(LocalDate dateOfBirth) {
        int age = Period.between(dateOfBirth, LocalDate.now()).getYears();
        if (age >= 18) {
            return true;
        } else return false;
    }
}
