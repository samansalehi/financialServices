package com.example.accountservice.repository;

import com.example.accountservice.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Customer findByCustomerId(String id);
}
