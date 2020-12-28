package com.example.transactionservice.controllers;

import com.example.transactionservice.dto.Account;
import com.example.transactionservice.dto.CustomerView;
import com.example.transactionservice.entities.Transaction;
import com.example.transactionservice.queries.TransactionsByAccountIdQuery;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(name = "/transactions")
public class TransactionsController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;
    private final AccountServicefeginClient accountServicefeginClient;


    public TransactionsController(CommandGateway commandGateway,
                                  QueryGateway queryGateway,
                                  AccountServicefeginClient accountServicefeginClient) {
        this.commandGateway = commandGateway;
        this.queryGateway=queryGateway;
        this.accountServicefeginClient = accountServicefeginClient;
    }

    @GetMapping(path = "/customerByAccountId/{id}", produces = "application/json")
    public CustomerView customerByAccountId(@PathVariable String id, HttpServletRequest request) {
        log(request);
        Account account = accountServicefeginClient.accountById(id);
        CustomerView customerView = accountServicefeginClient.findById(account.getCustomerId());
        customerView.setAccountBalance(account.getBalance());
        return customerView;
    }


    @GetMapping(path = "/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Transaction> ListUpdates(@RequestParam("id") String id, HttpServletRequest request) {
        log(request);
        return Flux.<Transaction> create(emitter -> {
            SubscriptionQueryResult<List<Transaction>, Transaction> queryResult =
                    queryGateway.subscriptionQuery(new TransactionsByAccountIdQuery(id),
                            ResponseTypes.multipleInstancesOf(Transaction.class),
                            ResponseTypes.instanceOf(Transaction.class));
            queryResult.initialResult().subscribe(transaction -> transaction.forEach(emitter::next));
            queryResult.updates().doOnComplete(emitter::complete).subscribe(emitter::next);
        });
    }

    private void log(HttpServletRequest request) {
        // @formatter:off
        StringBuilder headers = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            headers.append(String.format("%s: [%s]", name, request.getHeader(name)));
            if (headerNames.hasMoreElements()) {
                headers.append("\n");
            }
        }

        StringBuilder params = new StringBuilder();
        request.getParameterMap().entrySet().stream()
                .map(e->String.format(
                        "%s: %s",
                        e.getKey(),
                        Arrays.stream(e.getValue()).collect(Collectors.joining(", ", "[", "]"))))
                .collect(Collectors.joining("\n* "));

        System.out.println(String.format("URI: %s%s\nMETHOD: %s\nHEADERS:\n%s\nPARAMS:\n%s",
                request.getServletPath(),
                StringUtils.hasText(request.getQueryString()) ? "?" + request.getQueryString() : "",
                request.getMethod(),
                headers.toString(),
                params.toString()));
        // @formatter:on
    }
}
