package com.example.transactionservice.repositories;

import com.example.transactionservice.entities.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction,Long> {
}
