package com.example.accountservice;

import com.example.accountservice.aggregate.AccountAggregate;
import com.example.accountservice.commands.CreateAccountCommand;
import com.example.accountservice.commands.CreditMoneyCommand;
import com.example.accountservice.commands.DebitMoneyCommand;
import com.example.accountservice.entities.AccountStatus;
import com.example.accountservice.events.*;
import com.example.common.Currency;
import com.example.common.TransactionType;
import com.example.common.commands.TransactionCommand;
import com.example.common.events.TransactionEvent;
import org.axonframework.commandhandling.NoHandlerForCommandException;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.hamcrest.Matcher;
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

    //because Transaction command is handled by different service then throw NoHandlerForCommandException
    @Test
    public void createAccountCommandWithNonZeroAmountTest() {
        double amount = 100;
        Currency bitcoin = Currency.BITCOIN;
        fixture.given(new AccountActivatedEvent(accountId, AccountStatus.ACTIVE)).
                when(new TransactionCommand(transactionId, accountId,
                        amount, amount, bitcoin, TransactionType.CREDIT)).
                expectException(NoHandlerForCommandException.class);
    }

    @Test
    public void moneyCreditCommandTest() {
        double amount = 1200;
        Currency bitcoin = Currency.BITCOIN;
        fixture.given(new AccountCreatedEvent(accountId, amount, bitcoin, customerId)).
                when(new CreditMoneyCommand(accountId,
                        amount, bitcoin)).
                expectEvents(new MoneyCreditedEvent(accountId, amount, bitcoin));
    }


    @Test
    public void moneyCreditCommandWithAccountBalanceLowerThanZeroTest() {
        double amount = 1200;
        Currency bitcoin = Currency.BITCOIN;
        fixture.given(new AccountCreatedEvent(accountId, -1000, bitcoin, customerId)).
                when(new CreditMoneyCommand(accountId,
                        amount, bitcoin)).
                expectEvents(new MoneyCreditedEvent(accountId, amount, bitcoin),
                        new AccountActivatedEvent(accountId, AccountStatus.ACTIVE));
    }

    @Test
    public void moneyDebitCommandTest() {
        double amount = 1200;
        Currency bitcoin = Currency.BITCOIN;
        fixture.given(new AccountCreatedEvent(accountId, amount, bitcoin, customerId)).
                when(new DebitMoneyCommand(accountId,
                        amount, bitcoin)).
                expectEvents(new MoneyDebitedEvent(accountId, amount, bitcoin));
    }

    @Test
    public void moneyDebitCommandWithAmountMoreThanAccountBalanceTest() {
        double amount = 1200;
        Currency bitcoin = Currency.BITCOIN;
        fixture.given(new AccountCreatedEvent(accountId, 1000, bitcoin, customerId)).
                when(new DebitMoneyCommand(accountId,
                        amount, bitcoin)).
                expectEvents(new MoneyDebitedEvent(accountId, amount, bitcoin),
                        new AccountHeldEvent(accountId, AccountStatus.PENDING));
    }
}
