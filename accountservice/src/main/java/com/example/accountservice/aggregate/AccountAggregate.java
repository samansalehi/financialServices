package com.example.accountservice.aggregate;

import com.example.accountservice.commands.*;
import com.example.accountservice.entities.AccountStatus;
import com.example.accountservice.events.*;
import com.example.commoncommands.Currency;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;


@Aggregate
public class AccountAggregate {
    static final Logger log =
            LoggerFactory.getLogger(AccountAggregate.class);
    @AggregateIdentifier
    private String id;
    private String customerId;
    private double balance;
    private Currency curency;
    private AccountStatus accountStatus;

    public AccountAggregate() {
    }

    @CommandHandler
    public AccountAggregate(CreateAccountCommand createAccountCommand) {
        AggregateLifecycle.apply(new AccountCreatedEvent(createAccountCommand.id,
                createAccountCommand.getAccountBalance(), createAccountCommand.getCurrency(),
                createAccountCommand.getCustomerId()));
    }

    @EventSourcingHandler
    protected void on(AccountCreatedEvent accountCreatedEvent) {
        this.id = accountCreatedEvent.id;
        this.balance = accountCreatedEvent.getAccountBalance();
        this.curency = accountCreatedEvent.getCurrency();
        this.accountStatus = AccountStatus.OPEN;
        this.customerId = accountCreatedEvent.getCustomerId();
        AggregateLifecycle.apply(new AccountActivatedEvent(this.id, AccountStatus.ACTIVE));
        checkBalance(balance);
    }

    private void checkBalance(double balance) {
        if (balance > 0) {
            AggregateLifecycle.apply(new TransactionCreditEvent(UUID.randomUUID().toString(),
                    this.id, this.customerId, this.balance,this.curency));
        }
    }

    @EventSourcingHandler
    protected void on(AccountActivatedEvent accountActivatedEvent) {
        this.accountStatus = accountActivatedEvent.status;
    }

    @CommandHandler
    protected void on(CreditMoneyCommand creditMoneyCommand) {
        AggregateLifecycle.apply(new MoneyCreditedEvent(creditMoneyCommand.id, creditMoneyCommand.creditAmount, creditMoneyCommand.currency));
    }

    @EventSourcingHandler
    protected void on(MoneyCreditedEvent moneyCreditedEvent) {

        if (this.balance < 0 & (this.balance + moneyCreditedEvent.creditAmount) >= 0) {
            AggregateLifecycle.apply(new AccountActivatedEvent(this.id, AccountStatus.ACTIVE));
        }

        this.balance += moneyCreditedEvent.creditAmount;
    }

    @CommandHandler
    protected void on(DebitMoneyCommand debitMoneyCommand) {
        AggregateLifecycle.apply(new MoneyDebitedEvent(debitMoneyCommand.id, debitMoneyCommand.debitAmount, debitMoneyCommand.currency));
    }

    @EventSourcingHandler
    protected void on(MoneyDebitedEvent moneyDebitedEvent) {

        if (this.balance >= 0 & (this.balance - moneyDebitedEvent.debitAmount) < 0) {
            AggregateLifecycle.apply(new AccountHeldEvent(this.id, AccountStatus.PENDING));
        }

        this.balance -= moneyDebitedEvent.debitAmount;

    }

    @EventSourcingHandler
    protected void on(AccountHeldEvent accountHeldEvent) {
        this.accountStatus = accountHeldEvent.status;
    }
}
