package com.example.accountservice;

import com.example.accountservice.entities.Customer;
import com.example.accountservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

@Component
public class DummyData {
    @Autowired
    CustomerRepository customerRepository;
    @PostConstruct
    public void init()
    {
        Customer customer;
        for (int i = 0; i < 10; i++) {
            customer= new Customer();
            customer.setName("Saman"+i);
            customer.setFamily("Salehi"+i);
            customer.setSurname("Sami" +i);
            customer.setDateOfBirth(new Date());
            customerRepository.save(customer);
        }

    }
}
