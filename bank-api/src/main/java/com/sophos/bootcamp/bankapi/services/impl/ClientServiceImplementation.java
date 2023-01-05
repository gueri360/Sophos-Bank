package com.sophos.bootcamp.bankapi.services.impl;

import com.sophos.bootcamp.bankapi.entities.Client;
import com.sophos.bootcamp.bankapi.entities.Product;
import com.sophos.bootcamp.bankapi.exceptions.BadRequestException;
import com.sophos.bootcamp.bankapi.exceptions.NotFoundException;
import com.sophos.bootcamp.bankapi.repositories.ClientRepository;
import com.sophos.bootcamp.bankapi.repositories.ProductRepository;
import com.sophos.bootcamp.bankapi.services.ClientService;
import com.sophos.bootcamp.bankapi.utils.BankUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.sophos.bootcamp.bankapi.entities.enums.AccountStatus.CANCELLED;

@Service
public class ClientServiceImplementation implements ClientService {

    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;

    public ClientServiceImplementation(ClientRepository clientRepository,
                                       ProductRepository productRepository) {
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Optional<Client> findClientById(Long id) {
        if (clientRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Client does not exist");
        }
        return clientRepository.findById(id);
    }

    @Override
    public Client createClient(Client client) {
        Optional<Client> clientOpt = clientRepository.findByIdNumber(client.getIdNumber());
        if (clientOpt.isPresent()) {
            throw new BadRequestException("This client's id number: " + client.getIdNumber() + " already exists");
        }
        client.setEmailAddress(client.getEmailAddress().toLowerCase());
        Boolean emailValidator = BankUtils.validateEmailAddress(client.getEmailAddress());
        if (isClientOver18Yo(client.getDateOfBirth())) {
            client.setCreationDate(LocalDate.now());
            if (client.getNames().length() < 2 || client.getLastNames().length() < 2) {
                throw new BadRequestException("This parameter must have more than 2 characters");
            }
            if (!emailValidator) {
                throw new BadRequestException("Email address is incorrect");
            }
            client.setModificationDate(new Date());
            client.setCreationDate(LocalDate.now());
            Client clientSaved = clientRepository.save(client);
            return clientSaved;
        } else throw new BadRequestException("Client is underage");

    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    //TODO create a DTO for client modification
    public Client modifyClient(Client client) {
        Client clientExists = clientRepository.findById(client.getId()).orElseThrow(() -> new NotFoundException("This client does not exist in the system"));
        clientExists.setNames(client.getNames());
        clientExists.setLastNames(client.getLastNames());
        Boolean emailValidator = BankUtils.validateEmailAddress(client.getEmailAddress());
        if (!emailValidator) {
            throw new BadRequestException("Please provide a correct email pattern");
        }
        clientExists.setEmailAddress(client.getEmailAddress());
        Client modifiedClient = clientRepository.save(clientExists);
        return modifiedClient;
    }

    @Override
    public Boolean deleteClientById(Long id) {
        Client findClientById = clientRepository.findById(id).orElseThrow(() -> new NotFoundException("This client does not exist"));
        List<Product> products = productRepository.findAllByAccountCreatorId(findClientById.getId());
        List<Product> cancelledProducts = products.stream()
                .filter((p) -> p.getAccountStatus() == CANCELLED)
                .collect(Collectors.toList());
        if (products.size() != cancelledProducts.size()) {
            throw new BadRequestException("All accounts must be cancelled before removing client");
        }
        findClientById.setIsDeleted(true);
        clientRepository.save(findClientById);
        return true;
    }


    private Boolean isClientOver18Yo(LocalDate dateOfBirth) {
        int age = Period.between(dateOfBirth, LocalDate.now()).getYears();
        if (age >= 18) {
            return true;
        } else return false;
    }


}
