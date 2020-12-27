package com.example.accountservice;

import com.example.accountservice.entities.Customer;
import com.example.accountservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.UUID;

@Component
public class DummyData {
    @Autowired
    CustomerRepository customerRepository;

    @PostConstruct
    public void init() {
        Customer customer;
        customer = new Customer();
        customer.setCustomerId("1122000001");
        customer.setName("Saman");
        customer.setFamily("Salehi");
        customer.setSurname("Sami");
        customer.setDateOfBirth(new Date());
        customerRepository.save(customer);

        customer = new Customer();
        customer.setCustomerId("1122000002");
        customer.setName("Matin");
        customer.setFamily("Abbasi");
        customer.setSurname("Matin");
        customer.setDateOfBirth(new Date());
        customerRepository.save(customer);

        customer = new Customer();
        customer.setCustomerId("1122000003");
        customer.setName("Armando");
        customer.setFamily("Ramirez");
        customer.setSurname("Armando");
        customer.setDateOfBirth(new Date());
        customerRepository.save(customer);


    }
}
