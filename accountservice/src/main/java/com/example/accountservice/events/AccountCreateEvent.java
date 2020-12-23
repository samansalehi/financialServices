package com.example.accountservice.events;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class AccountCreateEvent {
    @TargetAggregateIdentifier
    private final long customer_id;
    private final long account_id;
    private double balance;

    public AccountCreateEvent(long customer_id, long account_id,double balance) {
        this.customer_id = customer_id;
        this.account_id = account_id;
    }

    public long getCustomer_id() {
        return customer_id;
    }

    public long getAccount_id() {
        return account_id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
