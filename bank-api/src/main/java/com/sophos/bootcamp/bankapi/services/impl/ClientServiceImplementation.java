package com.sophos.bootcamp.bankapi.services.impl;

import com.sophos.bootcamp.bankapi.entities.Client;
import com.sophos.bootcamp.bankapi.entities.Product;
import com.sophos.bootcamp.bankapi.exceptions.BadRequestException;
import com.sophos.bootcamp.bankapi.repositories.ClientRepository;
import com.sophos.bootcamp.bankapi.repositories.ProductRepository;
import com.sophos.bootcamp.bankapi.services.ClientService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
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
        return clientRepository.findById(id);
    }

    @Override
    public Client createClient(Client client) {
        //TODO make a verification to know if IdNumber has not been used before to create an account
//        List<Client> findClientByIdNumber = clientRepository.findClientByIdNumber(client.getIdNumber());
//        Boolean idDoesNotExistsInDb = findClientByIdNumber.stream()
//                .filter(p -> p.getIdNumber() == client.getIdNumber())
//                .collect(Collectors.toList()).isEmpty();
        if (isClientOver18Yo(client.getDateOfBirth())) {
            client.setCreationDate(LocalDate.now());
            if (client.getNames().length() < 2 || client.getLastNames().length() < 2) {
                throw new BadRequestException("This parameter must have more than 2 characters");
            }
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
        Optional<Client> verifyId = clientRepository.findById(client.getId());
        if (verifyId.isPresent()) {
            Client clientModified = clientRepository.save(client);
            return clientModified;
        } else {
            throw new BadRequestException("This Client is not registered in our system, please verify the id or create a Client");
        }
    }


    //Un cliente no podrá ser eliminado si tiene productos vinculados que no se
    //encuentren cancelados.
    @Override
    public Boolean deleteClientById(Long id) {
        Optional<Client> findClientById = clientRepository.findById(id);
        if (findClientById.isPresent()) {
            List<Product> products = productRepository.findAllByAccountCreatorId(id);
            List<Product> cancelledProducts = products.stream()
                    .filter((p) -> p.getAccountStatus() == CANCELLED)
                    .collect(Collectors.toList());
            if (products.size() != cancelledProducts.size()) {
                throw new BadRequestException("All accounts must be cancelled before removing client");
            }
            clientRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private Boolean isClientOver18Yo(LocalDate dateOfBirth) {
        int age = Period.between(dateOfBirth, LocalDate.now()).getYears();
        if (age >= 18) {
            return true;
        } else return false;
    }

//    private Boolean doesClientExists(String idNumber) {
//        List<Client> findClientByIdNumber = clientRepository.findClientByIdNumber(idNumber);
//        Boolean filterClientByIdNumber = findClientByIdNumber.stream()
//                .filter(p -> p.getIdNumber() == idNumber)
//                .collect(Collectors.toList()).isEmpty();
//    }


}
