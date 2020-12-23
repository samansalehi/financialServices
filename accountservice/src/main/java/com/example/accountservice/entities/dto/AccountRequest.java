package com.example.accountservice.entities.dto;


public class AccountRequest {
    private long customer_id;
    private double balance;

    public AccountRequest(long customer_id, double balance) {
        this.customer_id = customer_id;
        this.balance = balance;
    }

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
