package com.example.transactionservice.queries;

public class TransactionsByAccountIdQuery {
    private final String accountId;

    public TransactionsByAccountIdQuery(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountId() {
        return accountId;
    }
}
