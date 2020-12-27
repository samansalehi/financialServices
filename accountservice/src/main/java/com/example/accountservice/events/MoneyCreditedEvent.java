package com.example.accountservice.events;

import com.example.common.Currency;

public class MoneyCreditedEvent extends BaseEvent<String> {

    private final double creditAmount;
    private final Currency currency;

    public MoneyCreditedEvent(String id, double creditAmount, Currency currency) {
        super(id);
        this.creditAmount = creditAmount;
        this.currency = currency;
    }

    public double getCreditAmount() {
        return creditAmount;
    }


    public Currency getCurrency() {
        return currency;
    }
}
