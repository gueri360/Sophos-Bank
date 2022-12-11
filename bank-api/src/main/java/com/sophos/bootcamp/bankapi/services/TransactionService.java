package com.sophos.bootcamp.bankapi.services;

import com.sophos.bootcamp.bankapi.entities.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionService {

    Optional<Transaction> findTransactionById (Long id);

    Transaction createTransaction (Transaction transaction);

    List<Transaction> listOfTransactions ();

    //Recordar hacer la parte de actualizar el saldo disponible y saldo

}
