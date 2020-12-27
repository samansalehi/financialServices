package com.example.accountservice.entities.dto;


import com.example.common.Currency;

public class AccountRequest {
    private String customerId;
    private double balance;
    private Currency currency;

    public AccountRequest(String customer_id, double balance, Currency currency) {
        this.customerId = customer_id;
        this.balance = balance;
        this.currency =currency;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
