package com.example.accountservice.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class RegisterAccountCommand {
    @TargetAggregateIdentifier
    private final long customer_id;
    private final double balance;

    public RegisterAccountCommand(long customer_id, double balance) {
        this.customer_id = customer_id;
        this.balance = balance;
    }

    public long getCustomer_id() {
        return customer_id;
    }

    public double getBalance() {
        return balance;
    }
}
