package com.example.transactionservice.queries;

public class TransactionByIdQuery {
    private final long transaction_id;

    public TransactionByIdQuery(long transaction_id) {
        this.transaction_id = transaction_id;
    }

    public long getTransaction_id() {
        return transaction_id;
    }
}
