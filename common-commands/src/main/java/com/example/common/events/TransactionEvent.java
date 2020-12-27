package com.example.common.events;

import com.example.common.Currency;
import com.example.common.TransactionType;

public class TransactionEvent extends BaseEvent<String> {
    private final String accountId;
    private final double balance;
    private final double amount;
    private final Currency currency;
    private final TransactionType transactionType;

    public TransactionEvent(String id, String accountId, double balance,
                            double amount, Currency currency, TransactionType transactionType) {
        super(id);
        this.accountId = accountId;
        this.balance = balance;
        this.amount = amount;
        this.transactionType = transactionType;
        this.currency = currency;
    }


    public String getAccountId() {
        return accountId;
    }

    public double getBalance() {
        return balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public double getAmount() {
        return amount;
    }
}
