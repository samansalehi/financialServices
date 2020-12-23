package com.example.transactionservice.aggregates;

import com.example.transactionservice.commands.TransactionCommand;
import com.example.transactionservice.entities.Transaction;
import com.example.transactionservice.entities.TransactionType;
import com.example.transactionservice.events.TransactionDepositEvent;
import com.example.transactionservice.repositories.TransactionRepository;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

@Aggregate
public class TransactionAggregate {
    static final Logger log =
            LoggerFactory.getLogger(TransactionAggregate.class);
    @Autowired
    TransactionRepository transactionRepository;

    @CommandHandler
    public void transactionDeposit(TransactionCommand transactionCommand) {
        Assert.notNull(transactionCommand.getAmount(), "Transaction must have an amount");
        Assert.notNull(transactionCommand.getFromAccount_id(), "From Account must not be null");
        Assert.notNull(transactionCommand.getToAccount_id(), "To Account must not be null");

        Transaction transaction = new Transaction();
        transaction.setFromAccount_id(transactionCommand.getFromAccount_id());
        transaction.setToAccount_id(transactionCommand.getToAccount_id());
        transaction.setAmount(transactionCommand.getAmount());
        transaction.setTransactionType(TransactionType.CASHDEPOSIT);
        Transaction persistTransaction = transactionRepository.save(transaction);
        AggregateLifecycle.apply(new TransactionDepositEvent(persistTransaction.getId()));
    }

    @EventSourcingHandler
    public void transactionDepositEventHandler(TransactionDepositEvent transactionDepositEvent) {
        log.info("new transaction with id {} is created", transactionDepositEvent.getTransaction_id());
    }
}
