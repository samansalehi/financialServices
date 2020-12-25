package com.example.commoncommands;


public class TransactionCreditCommand extends BaseCommand<String> {

    private final String accountId;
    private final String customerId;
    private final double amount;
    private final Currency currency;


    public TransactionCreditCommand(String id, String accountId, String customerId,
                                    double amount, Currency currency) {
        super(id);
        this.accountId = accountId;
        this.customerId = customerId;
        this.amount = amount;
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Currency getCurrency() {
        return currency;
    }
}
