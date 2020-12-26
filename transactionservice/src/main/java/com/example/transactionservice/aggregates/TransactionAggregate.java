package com.example.transactionservice.aggregates;

import com.example.commoncommands.Currency;
import com.example.commoncommands.TransactionCreditCommand;
import com.example.transactionservice.entities.Transaction;
import com.example.transactionservice.events.TransactionCreditEvent;
import com.example.transactionservice.queries.AllTransactionQuery;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Aggregate
public class TransactionAggregate {
    static final Logger log =
            LoggerFactory.getLogger(TransactionAggregate.class);

    @AggregateIdentifier
    private String id;
    private String customerId;
    private String accountId;
    private double amount;
    private Currency currency;

    @CommandHandler
    public TransactionAggregate(TransactionCreditCommand transactionCreditCommand) {
        Assert.notNull(transactionCreditCommand.getAmount(), "Transaction must have an amount");
        Assert.notNull(transactionCreditCommand, "Account id  must not be null");
        this.id=transactionCreditCommand.id;
        this.customerId=transactionCreditCommand.getCustomerId();
        this.accountId=transactionCreditCommand.getAccountId();
        this.amount=transactionCreditCommand.getAmount();
        this.currency=transactionCreditCommand.getCurrency();
        AggregateLifecycle.apply(new TransactionCreditEvent(id,customerId,accountId,amount));
    }


}
