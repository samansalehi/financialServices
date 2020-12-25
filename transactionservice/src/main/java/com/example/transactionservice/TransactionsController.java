package com.example.transactionservice;

import com.example.commoncommands.TransactionCreditCommand;
import com.example.transactionservice.entities.Transaction;
import com.example.transactionservice.queries.AllTransactionQuery;
import com.example.transactionservice.requests.CreateTransactionRequest;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(name = "/transactions")
public class TransactionsController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public TransactionsController(CommandGateway commandGateway,QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway=queryGateway;
    }

    @PostMapping(path = "credit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<String> createTransaction(@RequestBody CreateTransactionRequest requestBody, HttpServletRequest request) {
        return commandGateway.send(new TransactionCreditCommand(UUID.randomUUID().toString(),
                requestBody.getAccountId(),requestBody.getCustomerId(),requestBody.getAmount(),requestBody.getCurrency()));
    }



    @GetMapping(path = "/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Transaction> ListUpdates(@RequestParam("type") String basketType, HttpServletRequest request) {
        return Flux.<Transaction> create(emitter -> {
            SubscriptionQueryResult<List<Transaction>, Transaction> queryResult =
                    queryGateway.subscriptionQuery(new AllTransactionQuery(),
                            ResponseTypes.multipleInstancesOf(Transaction.class),
                            ResponseTypes.instanceOf(Transaction.class));
            queryResult.initialResult().subscribe(transaction -> transaction.forEach(emitter::next));
            queryResult.updates().doOnComplete(emitter::complete).subscribe(emitter::next);
        });
    }
}
