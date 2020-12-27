package com.example.accountservice.events;

import com.example.accountservice.entities.AccountStatus;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class AccountActivatedEvent extends BaseEvent<String> {

    public final AccountStatus status;

    public AccountActivatedEvent(String id, AccountStatus status) {
        super(id);
        this.status = status;
    }
}
