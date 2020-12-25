package com.example.transactionservice;

import com.example.transactionservice.entities.Transaction;
import com.example.transactionservice.events.TransactionCreditEvent;
import com.example.transactionservice.queries.AllTransactionQuery;
import com.example.transactionservice.queries.TransactionByIdQuery;
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
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Component
public class TransactionsViewProjector {

  private static final Logger LOGGER = LoggerFactory.getLogger(TransactionsViewProjector.class);

  private TransactionRepository repository;
  private QueryUpdateEmitter queryUpdateEmitter;


  @Autowired
  public TransactionsViewProjector(TransactionRepository repository, QueryUpdateEmitter queryUpdateEmitter) {
    this.repository = repository;
    this.queryUpdateEmitter = queryUpdateEmitter;
  }


  @QueryHandler
  public Transaction handle(TransactionByIdQuery query) {
    return repository.findById(query.getTransaction_id()).get();
  }


  @QueryHandler
  public List<Transaction> handle(AllTransactionQuery query) {
    Example<Transaction> example = Example.of(new Transaction(),
        ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING));
    return StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
  }

  @EventHandler
  public void on(TransactionCreditEvent event, @SequenceNumber long aggregateVersion, @Timestamp Instant occurrenceInstant) {
    Transaction view = new Transaction();
    view.setTransactionId(event.id);
    queryUpdateEmitter.emit(AllTransactionQuery.class,query ->view.getId() >0, view);
  }
}