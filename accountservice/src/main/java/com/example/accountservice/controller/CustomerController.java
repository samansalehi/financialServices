package com.example.accountservice.controller;

import com.example.accountservice.entities.Customer;
import com.example.accountservice.repository.CustomerRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
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

    @GetMapping("/{id}")
    @ResponseBody
    public Customer findById(@PathVariable long id) {
        return customerRepository.findById(id).get();
    }
}
