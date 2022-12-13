package com.sophos.bootcamp.bankapi.services.impl;

import com.sophos.bootcamp.bankapi.entities.Client;
import com.sophos.bootcamp.bankapi.entities.Product;
import com.sophos.bootcamp.bankapi.entities.enums.AccountStatus;
import com.sophos.bootcamp.bankapi.entities.enums.AccountType;
import com.sophos.bootcamp.bankapi.exceptions.BadRequestException;
import com.sophos.bootcamp.bankapi.exceptions.NotFoundException;
import com.sophos.bootcamp.bankapi.repositories.ClientRepository;
import com.sophos.bootcamp.bankapi.repositories.ProductRepository;
import com.sophos.bootcamp.bankapi.services.ProductService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static com.sophos.bootcamp.bankapi.entities.enums.AccountStatus.CANCELLED;
import static com.sophos.bootcamp.bankapi.entities.enums.AccountType.CHECKING;
import static com.sophos.bootcamp.bankapi.entities.enums.AccountType.DEBIT;

@Service
public class ProductServiceImplementation implements ProductService {

    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;

    public ProductServiceImplementation(ProductRepository productRepository, ClientRepository clientRepository) {
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
    }

    //TODO Create a method that looks for the account number to be unique
    @Override
    public Product createProduct(Product product) {
        product.setAccountNumber(generateAccountNumber(product.getAccountType()));
        Client findClient = clientRepository.findById(product.getAccountCreator().getId())
                .orElseThrow(() -> new NotFoundException("Client not found"));
//      List<Product> byAccountCreatorId = productRepository.findByAccountCreator(product.getAccountCreator().getId());
        product.setAccountCreator(findClient);
        product.setAccountStatus(AccountStatus.ACTIVE);
        product.setBalance(0.0);
        product.setAvailableBalance(0.0);
        product.setCreationDate(new Date());
        product.setModificationDate(new Date());
        Product createdProduct = productRepository.save(product);
        return createdProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Boolean deleteProductById(Long id) {
        return getProductById(id).map(product -> {
            productRepository.deleteById(id);
            return true;
        }).orElse(false);

    }

    @Override
    public Product modifyProduct(Product product) {
        Product productExists = productRepository.findById(product.getId()).orElseThrow(() -> new IllegalArgumentException("Product is not in DBs"));
        if (CANCELLED.equals(product.getAccountStatus()) && (productExists.getBalance() < 0 || productExists.getBalance() > 1)) {
            throw new IllegalArgumentException("Account can not be closed if Balance is under 0.0");
        }
        productExists.setAccountStatus(product.getAccountStatus());
        Product modifiedProduct = productRepository.save(productExists);
        return modifiedProduct;
    }

    private String getRandomNum(String initialData) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer(initialData);
        int max = 10;
        for (int i = 0; i < 8; i++) {
            int randomized = random.nextInt(max);
            sb.append(randomized);
        }

        return sb.toString();
    }

    private String generateAccountNumber(AccountType type) {
        if (type == DEBIT) {
            return getRandomNum("46");
        } else if (type == CHECKING) {
            return getRandomNum("23");
        }
        return "Not supported type";
    }


}
