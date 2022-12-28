package com.sophos.bootcamp.bankapi.repositories;

import com.sophos.bootcamp.bankapi.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query(value="SELECT * FROM transaction WHERE recipient_id = ?1 OR sender_id = ?1", nativeQuery = true)
    List<Transaction> findAllBySenderIdOrRecipientId(Long id);

}
