package com.example.transactionservice.repositories;

import com.example.transactionservice.entities.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction,Long> {
    List<Transaction> findAllByAccountId(String id);
    Transaction findTransactionByTransactionId(String id);
}
