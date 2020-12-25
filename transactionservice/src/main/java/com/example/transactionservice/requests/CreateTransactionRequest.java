package com.example.transactionservice.requests;

import com.example.commoncommands.Currency;

public class CreateTransactionRequest {
    private  String accountId;
    private  String customerId;
    private Currency currency;
    private  double amount;

    public CreateTransactionRequest(String accountId, String customerId, double amount) {
        this.accountId = accountId;
        this.customerId = customerId;
        this.amount = amount;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
