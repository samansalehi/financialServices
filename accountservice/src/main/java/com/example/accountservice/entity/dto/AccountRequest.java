package com.example.accountservice.entity.dto;

import org.springframework.stereotype.Component;


public class AccountRequest {
    private long customer_id;
    private double balance;

    public long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(long customer_id) {
        this.customer_id = customer_id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
