package com.example.accountservice.repository;

import com.example.accountservice.entities.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    Account findByAccountId(String id);
}
