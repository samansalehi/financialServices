package com.example.transactionservice.events;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class TransactionDepositEvent {
    @TargetAggregateIdentifier
    private final long transaction_id;

    public TransactionDepositEvent(long transaction_id) {
        this.transaction_id = transaction_id;
    }

    public long getTransaction_id() {
        return transaction_id;
    }
}
