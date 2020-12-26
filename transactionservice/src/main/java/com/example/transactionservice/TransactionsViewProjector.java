package com.example.transactionservice;

import com.example.transactionservice.controllers.AccountServicefeginClient;
import com.example.transactionservice.dto.TransactionView;
import com.example.transactionservice.entities.Transaction;
import com.example.transactionservice.entities.TransactionType;
import com.example.transactionservice.events.TransactionCreditEvent;
import com.example.transactionservice.queries.AllTransactionQuery;
import com.example.transactionservice.queries.TransactionByIdQuery;
import com.example.transactionservice.queries.TransactionsByAccountIdQuery;
import com.example.transactionservice.repositories.TransactionRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.SequenceNumber;
import org.axonframework.eventhandling.Timestamp;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Component
public class TransactionsViewProjector {

  private static final Logger LOGGER = LoggerFactory.getLogger(TransactionsViewProjector.class);

  private TransactionRepository repository;
  private QueryUpdateEmitter queryUpdateEmitter;

  @Autowired
  public TransactionsViewProjector(TransactionRepository repository,
                                   QueryUpdateEmitter queryUpdateEmitter) {
    this.repository = repository;
    this.queryUpdateEmitter = queryUpdateEmitter;
  }


  @QueryHandler
  public Transaction handle(TransactionByIdQuery query) {
    return repository.findTransactionByTransactionId(query.getTransaction_id());
  }

  @QueryHandler
  public List<Transaction> handle(TransactionsByAccountIdQuery query) {
    return StreamSupport.stream(repository.findAllByAccountId(query.getAccountId()).spliterator(), false).collect(Collectors.toList());
  }

  @QueryHandler
  public List<Transaction> handle(AllTransactionQuery query) {
    Example<Transaction> example = Example.of(new Transaction(),
        ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
    return StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
  }

  @EventHandler
  public void on(TransactionCreditEvent event, @SequenceNumber long aggregateVersion, @Timestamp Instant occurrenceInstant) {
    Transaction view = new Transaction();
    view.setTransactionId(event.id);
    view.setTransactionType(TransactionType.CASHDEPOSIT);
    view.setAmount(event.getAmount());
    view.setAccountId(event.getAccountId());
    view.setDate(new Date());
    save(view);
    queryUpdateEmitter.emit(TransactionsByAccountIdQuery.class,query ->view.getAccountId().equals(event.getAccountId()) , view);
  }

  private void save(Transaction transaction) {
    repository.save(transaction);
    LOGGER.info("emitting update: {}", transaction);

    // emit the updated basket view to all subscribers
    queryUpdateEmitter.emit(TransactionsByAccountIdQuery.class, query -> test(query, transaction), transaction);
  }

  private boolean test(TransactionsByAccountIdQuery query, Transaction transaction) {
    boolean res = query.getAccountId().equals(transaction.getAccountId());
    if (res) {
      LOGGER.info("HIT  -- ID: " + query.getAccountId());
    } else {
      LOGGER.info("MISS -- ID: " + query.getAccountId());
    }
    return res;
  }
}