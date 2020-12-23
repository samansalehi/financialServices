package com.example.accountservice.commands;


import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class ActivateAccountCommand {
    @TargetAggregateIdentifier
    private final long account_id;

    public ActivateAccountCommand(long account_id) {
        this.account_id = account_id;
    }

    public long getAccount_id() {
        return account_id;
    }
}
