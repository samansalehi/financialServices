package com.example.transactionservice.queries;

public class TransactionByIdQuery {
    private final String transaction_id;

    public TransactionByIdQuery(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getTransaction_id() {
        return transaction_id;
    }
}
