package com.example.transactionservice.events;

public class TransactionCreditEvent extends BaseEvent<String>{
    private final String customerId;
    private final String accountId;
    private final double amount;
    public TransactionCreditEvent(String id, String customerId, String accountId, double amount) {
        super(id);
        this.customerId = customerId;
        this.accountId = accountId;
        this.amount = amount;
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
}
