package com.example.accountservice.commands;


import com.example.common.commands.BaseCommand;
import com.example.common.Currency;

public class CreateAccountCommand extends BaseCommand<String> {

    private final double accountBalance;
    private final String customerId;
    private final Currency currency;

    public CreateAccountCommand(String id, double accountBalance, Currency currency, String customerId) {
        super(id);
        this.accountBalance = accountBalance;
        this.currency = currency;
        this.customerId = customerId;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Currency getCurrency() {
        return currency;
    }
}
