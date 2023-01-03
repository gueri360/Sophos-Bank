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
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import static com.sophos.bootcamp.bankapi.entities.enums.AccountStatus.CANCELLED;
import static com.sophos.bootcamp.bankapi.entities.enums.AccountType.CHECKING;
import static com.sophos.bootcamp.bankapi.entities.enums.AccountType.SAVINGS;

@Service
public class ProductServiceImplementation implements ProductService {

    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;

    public ProductServiceImplementation(ProductRepository productRepository, ClientRepository clientRepository) {
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public Product createProduct(Product product){
        Client findClient = clientRepository.findById(product.getAccountCreator().getId())
                .orElseThrow(() -> new NotFoundException("Client not found"));
        List<Product> byAccountCreatorId = productRepository.findAllByAccountCreatorId(product.getAccountCreator().getId());
        boolean hasGmfActive = byAccountCreatorId.stream()
                .filter((p) -> p.getGmfExempt().equals(true))
                .collect(Collectors.toList()).isEmpty();
        if(!hasGmfActive){
            product.setGmfExempt(false);
        }
        product.setAccountCreator(findClient);
        product.setAccountStatus(AccountStatus.ACTIVE);
        product.setBalance(0.0);
        product.setAvailableBalance(0.0);
        product.setCreationDate(new Date());
        product.setModificationDate(new Date());
        Product createdProduct = productRepository.save(product);
        //product has to be saved twice so the generated account number can be added to Id generator requirements (uniqueness)
        product.setAccountNumber(generateAccountNumber(product.getAccountType(), createdProduct.getId()));
        createdProduct = productRepository.save(product);
        return createdProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getAllProductsByClientId(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new NotFoundException("This client does not exist"));
        List<Product> products = productRepository.findAllByAccountCreatorId(client.getId());
        return products;
    }
    @Override
    public Optional<Product> getProductById(Long id) {
        if (productRepository.findById(id).isEmpty()){
            throw new NotFoundException("Product does not exist");
        }
        return productRepository.findById(id);
    }

    @Override
    @Transactional
    public Product modifyProduct(Product product) {
        Product productExists = productRepository.findById(product.getId()).orElseThrow(() -> new NotFoundException("Product is not in system"));
        if (CANCELLED.equals(product.getAccountStatus()) && (productExists.getBalance() < 0 || productExists.getBalance() > 1)) {
            throw new BadRequestException("Account can not be closed as it has a balance");
        }
        productExists.setAccountStatus(product.getAccountStatus());
        //Validates if Client has any products with GMF exempt
        if (product.getGmfExempt() && product.getGmfExempt() != productExists.getGmfExempt()){
            List<Product> productsByClient = productRepository.findAllByAccountCreatorId(productExists.getAccountCreator().getId());
            List<Product> productWithGmfExempt = productsByClient.stream()
                    .filter(p -> p.getId() != productExists.getId())
                    .filter(p -> p.getGmfExempt())
                    .collect(Collectors.toList());
            productWithGmfExempt.forEach( p -> {
                p.setGmfExempt(false);
                productRepository.save(p);
            });
        }
        productExists.setGmfExempt(product.getGmfExempt());
        Product modifiedProduct = productRepository.save(productExists);
        return modifiedProduct;
    }

    public String getAccountNumber(String initialData, String productNumber) {
        StringBuffer sb = new StringBuffer(initialData);
        int max = 10;
        for (int i = 0; i < 8 - productNumber.length(); i++) {
            sb.append("0");
        }
        sb.append(productNumber);
        return sb.toString();
    }

    private String generateAccountNumber(AccountType type, Long productId) {
        if (type == SAVINGS) {
            return getAccountNumber("46", productId.toString());
        } else if (type == CHECKING) {
            return getAccountNumber("23", productId.toString());
        }
        return "Not supported type";
    }


}
