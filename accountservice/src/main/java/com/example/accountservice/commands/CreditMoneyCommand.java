package com.example.accountservice.commands;

import com.example.common.commands.BaseCommand;
import com.example.common.Currency;

public class CreditMoneyCommand extends BaseCommand<String> {

    private final double creditAmount;
    private final Currency currency;

    public CreditMoneyCommand(String id, double creditAmount, Currency currency) {
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
