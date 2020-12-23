package com.example.accountservice.events;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class AccountActivatedEvent {
    @TargetAggregateIdentifier
    private final long account_id;

    public AccountActivatedEvent(long account_id) {
        this.account_id = account_id;
    }
}
