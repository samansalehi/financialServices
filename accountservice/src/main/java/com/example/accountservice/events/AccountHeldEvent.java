package com.example.accountservice.events;

import com.example.accountservice.entities.AccountStatus;

public class AccountHeldEvent extends BaseEvent<String> {

    public final AccountStatus status;

    public AccountHeldEvent(String id, AccountStatus status) {
        super(id);
        this.status = status;
    }
}
