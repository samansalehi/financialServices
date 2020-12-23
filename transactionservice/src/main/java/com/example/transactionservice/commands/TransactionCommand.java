package com.example.transactionservice.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class TransactionCommand {
    private final long fromAccount_id;
    @TargetAggregateIdentifier
    private final long toAccount_id;
    private final double amount;

    public TransactionCommand(long fromAccount_id, long toAccount_id, double amount) {
        this.fromAccount_id = fromAccount_id;
        this.toAccount_id = toAccount_id;
        this.amount = amount;
    }

    public long getFromAccount_id() {
        return fromAccount_id;
    }

    public long getToAccount_id() {
        return toAccount_id;
    }

    public double getAmount() {
        return amount;
    }
}
