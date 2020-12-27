package com.example.accountservice.commands;

import com.example.common.commands.BaseCommand;
import com.example.common.Currency;

public class DebitMoneyCommand extends BaseCommand<String> {

    private final double debitAmount;
    private final Currency currency;

    public DebitMoneyCommand(String id, double debitAmount, Currency currency) {
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
