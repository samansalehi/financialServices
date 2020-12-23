package com.example.accountservice.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class TransactionCreateCommand {
    @TargetAggregateIdentifier
    private final  long account_id;
    private final double balance;

    public TransactionCreateCommand(long account_id, double balance) {
        this.account_id = account_id;
        this.balance = balance;
    }
}
