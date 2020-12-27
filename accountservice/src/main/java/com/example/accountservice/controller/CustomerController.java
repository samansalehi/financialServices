package com.example.accountservice.controller;

import com.example.accountservice.entities.Customer;
import com.example.accountservice.repository.CustomerRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
public class CustomerController {

    private CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/all")
    @ResponseBody
    public Iterable<Customer> allCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping("/customer/{id}")
    @ResponseBody
    public Customer findById(@PathVariable String id) {
        return customerRepository.findByCustomerId(id);
    }
}
