package com.example.accountservice.events;

import com.example.common.Currency;

public class MoneyDebitedEvent extends BaseEvent<String> {

    public final double debitAmount;
    public final Currency currency;

    public MoneyDebitedEvent(String id, double debitAmount, Currency currency) {
        super(id);
        this.debitAmount = debitAmount;
        this.currency = currency;
    }

    public double getDebitAmount() {
        return debitAmount;
    }


    public Currency getCurrency() {
        return currency;
    }
}
