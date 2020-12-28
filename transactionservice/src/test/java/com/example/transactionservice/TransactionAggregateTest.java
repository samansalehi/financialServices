package com.example.transactionservice;

import com.example.common.Currency;
import com.example.common.TransactionType;
import com.example.common.commands.TransactionCommand;
import com.example.common.events.TransactionEvent;
import com.example.transactionservice.aggregates.TransactionAggregate;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class TransactionAggregateTest {
    private FixtureConfiguration<TransactionAggregate> fixture;
    private String accountId;
    private String customerId;
    private String transactionId;

    @BeforeEach
    public void setUp() {
        fixture = new AggregateTestFixture<>(TransactionAggregate.class);
        accountId = UUID.randomUUID().toString();
        customerId = UUID.randomUUID().toString();
        transactionId = UUID.randomUUID().toString();
    }

    @Test
    public void TransactionCreditCommandTest() {
        double amount = 100;
        Currency currency = Currency.EURO;
        fixture.given().
                when(new TransactionCommand(transactionId, accountId, amount, amount,
                        currency, TransactionType.CREDIT)).
                expectSuccessfulHandlerExecution().
                expectEvents(new TransactionEvent(transactionId, accountId, amount, amount,
                        currency, TransactionType.CREDIT));
    }

    @Test
    public void TransactionDebitCommandTest() {
        double amount = 100;
        Currency currency = Currency.EURO;
        fixture.given().
                when(new TransactionCommand(transactionId, accountId, amount, amount,
                        currency, TransactionType.DEBIT)).
                expectSuccessfulHandlerExecution().
                expectEvents(new TransactionEvent(transactionId, accountId, amount, amount,
                        currency, TransactionType.DEBIT));
    }
}
