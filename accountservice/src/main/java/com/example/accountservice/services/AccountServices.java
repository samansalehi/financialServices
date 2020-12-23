package com.example.accountservice.services;

import com.example.accountservice.entities.AccountStatus;
import com.example.accountservice.entities.AccountType;
import com.example.accountservice.entities.dto.AccountRequest;
import com.example.accountservice.entities.dto.AccountResponse;
import com.example.accountservice.repository.AccountRepository;
import com.example.accountservice.repository.CustomerRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountServices {
    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;

    public AccountServices(AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    @RequestMapping(value = "/create",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public AccountResponse createAccount(@RequestBody AccountRequest request) {
        return new AccountResponse(1, AccountStatus.OPEN, AccountType.CREDIT);
    }
}
