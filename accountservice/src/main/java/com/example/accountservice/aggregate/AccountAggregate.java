package com.example.accountservice.aggregate;

import com.example.accountservice.commands.*;
import com.example.accountservice.entities.AccountStatus;
import com.example.accountservice.events.*;
import com.example.common.Currency;
import com.example.common.TransactionType;
import com.example.common.events.TransactionEvent;
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
    protected void on(AccountCreatedEvent event) {
        log.info("AccountCreatedEvent is handled with id {}", event.id);
        this.id = event.id;
        this.balance = event.getAccountBalance();
        this.curency = event.getCurrency();
        this.accountStatus = AccountStatus.CREATE;
        this.customerId = event.getCustomerId();
        log.info("AccountCreatedEvent is handled with id {}", event.id);
        this.accountStatus = AccountStatus.OPEN;
        AggregateLifecycle.apply(new AccountActivatedEvent(event.id, AccountStatus.ACTIVE));
    }

    private void checkBalance(double balance) {
        if (balance > 0) {
            log.info("transaction balance {} > 0 so TransactionCreditEvent push", balance);
            AggregateLifecycle.apply(new TransactionEvent(UUID.randomUUID().toString(), this.id,
                    this.balance, this.balance, this.curency, TransactionType.CREDIT));
        }
    }

    @EventSourcingHandler
    protected void on(AccountActivatedEvent accountActivatedEvent) {
        this.accountStatus = accountActivatedEvent.status;
//        checkBalance(balance);
    }

    @CommandHandler
    protected void on(CreditMoneyCommand creditMoneyCommand) {
        log.info("CreditMoneyCommand is handled with id {}", creditMoneyCommand.id);
        AggregateLifecycle.apply(new MoneyCreditedEvent(creditMoneyCommand.id, creditMoneyCommand.getCreditAmount(),
                creditMoneyCommand.getCurrency()));
    }

    @EventSourcingHandler
    protected void on(MoneyCreditedEvent moneyCreditedEvent) {
        this.id = moneyCreditedEvent.id;
        log.info("MoneyCreditedEvent is handled with id {}", moneyCreditedEvent.id);
        if (this.balance < 0 & (this.balance + moneyCreditedEvent.getCreditAmount()) >= 0) {
            AggregateLifecycle.apply(new AccountActivatedEvent(this.id, AccountStatus.ACTIVE));
        }
        this.balance += moneyCreditedEvent.getCreditAmount();
    }

    @CommandHandler
    protected void on(DebitMoneyCommand debitMoneyCommand) {
        AggregateLifecycle.apply(new MoneyDebitedEvent(debitMoneyCommand.id, debitMoneyCommand.getDebitAmount(),
                debitMoneyCommand.getCurrency()));
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
