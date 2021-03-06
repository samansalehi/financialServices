package com.example.transactionservice.controllers;

import com.example.transactionservice.dto.Account;
import com.example.transactionservice.dto.CustomerView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("account-service")
public interface AccountServicefeginClient {
    @GetMapping("/customer/{id}")
    public CustomerView findById(@PathVariable String id);

    @GetMapping(value = "/account/accountById/{id}")
    @ResponseBody
    public Account accountById(@PathVariable String id);

}
