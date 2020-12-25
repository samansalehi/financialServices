package com.example.accountservice.events;


import com.example.commoncommands.Currency;

public class TransactionCreditEvent extends BaseEvent<String>{
    private final String customerId;
    private final String accountId;
    private final double amount;
    private final Currency currency;
    public TransactionCreditEvent(String id, String customerId,
                                  String accountId, double amount,Currency currency) {
        super(id);
        this.customerId = customerId;
        this.accountId = accountId;
        this.amount = amount;
        this.currency=currency;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getAccountId() {
        return accountId;
    }

    public double getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }
}
