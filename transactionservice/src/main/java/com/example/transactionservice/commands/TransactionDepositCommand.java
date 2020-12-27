package com.example.transactionservice.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class TransactionDepositCommand {
    @TargetAggregateIdentifier
    private final  long account_id;
    private final double balance;

    public TransactionDepositCommand(long account_id, double balance) {
        this.account_id = account_id;
        this.balance = balance;
    }
}
