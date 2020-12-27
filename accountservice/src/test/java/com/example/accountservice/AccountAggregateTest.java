package com.example.accountservice;

import com.example.accountservice.aggregate.AccountAggregate;
import com.example.accountservice.commands.CreateAccountCommand;
import com.example.accountservice.entities.AccountStatus;
import com.example.accountservice.events.AccountActivatedEvent;
import com.example.accountservice.events.AccountCreatedEvent;
import com.example.common.Currency;
import com.example.common.TransactionType;
import com.example.common.events.TransactionEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class AccountAggregateTest {
    private FixtureConfiguration<AccountAggregate> fixture;
    private String accountId;
    private String customerId;
    private String transactionId;

    @BeforeEach
    public void setUp() {
        fixture = new AggregateTestFixture<>(AccountAggregate.class);
        accountId = UUID.randomUUID().toString();
        customerId = UUID.randomUUID().toString();
        transactionId = UUID.randomUUID().toString();
    }

    @Test
    public void createAccountCommandWithZeroAmountTest() {
        int accountBalance = 0;
        Currency doller = Currency.DOLLER;
        fixture.given().
                when(new CreateAccountCommand(accountId,
                        accountBalance, doller, customerId)).
                expectSuccessfulHandlerExecution().
                expectEvents(new AccountCreatedEvent(accountId,
                                accountBalance, doller, customerId),
                        new AccountActivatedEvent(accountId, AccountStatus.ACTIVE));
    }

    @Test
    public void createAccountCommandWithNonZeroAmountTest() {
        double amount = 100;
        Currency bitcoin = Currency.BITCOIN;
        fixture.given().
                when(new CreateAccountCommand(accountId,
                        amount, bitcoin, customerId)).
                expectSuccessfulHandlerExecution().
                expectEvents(new AccountCreatedEvent(accountId,
                                amount, bitcoin, customerId),
                        new AccountActivatedEvent(accountId, AccountStatus.ACTIVE),
                        new TransactionEvent(accountId, customerId, amount, amount,
                                bitcoin, TransactionType.CREDIT));
    }
}
