package com.sophos.bootcamp.bankapi.repositories;

import com.sophos.bootcamp.bankapi.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
