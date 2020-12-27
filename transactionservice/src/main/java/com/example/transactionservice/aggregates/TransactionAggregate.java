package com.example.transactionservice.aggregates;

import com.example.common.Currency;
import com.example.common.TransactionType;
import com.example.common.commands.TransactionCommand;
import com.example.common.events.TransactionEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;


@Aggregate
public class TransactionAggregate {
    static final Logger log =
            LoggerFactory.getLogger(TransactionAggregate.class);

    @AggregateIdentifier
    private String id;
    private String accountId;
    private double amount;
    private double balance;
    private Currency currency;
    private TransactionType transactionType;

    @CommandHandler
    public TransactionAggregate(TransactionCommand transactionCommand) {
        Assert.notNull(transactionCommand.getAmount(), "Transaction must have an amount");
        Assert.notNull(transactionCommand, "Account id  must not be null");
        this.id = transactionCommand.id;
        this.accountId = transactionCommand.getAccountId();
        this.amount = transactionCommand.getAmount();
        this.balance = transactionCommand.getBalance();
        this.currency = transactionCommand.getCurrency();
        this.transactionType = transactionCommand.getTransactionType();
        AggregateLifecycle.apply(new TransactionEvent(id, accountId, balance, amount, currency, transactionType));
    }


}
