package com.example.common.commands;


import com.example.common.Currency;
import com.example.common.TransactionType;

public class TransactionCommand extends BaseCommand<String> {

    private final String accountId;
    private final double amount;
    private final double balance;
    private final Currency currency;
    private final TransactionType transactionType;


    public TransactionCommand(String id, String accountId, double amount, double balance,
                              Currency currency, TransactionType transactionType) {
        super(id);
        this.accountId = accountId;
        this.amount = amount;
        this.balance = balance;
        this.currency = currency;
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public String getAccountId() {
        return accountId;
    }

    public double getBalance() {
        return balance;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public Currency getCurrency() {
        return currency;
    }
}
