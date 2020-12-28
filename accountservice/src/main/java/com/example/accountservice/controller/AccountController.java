package com.example.accountservice.controller;

import com.example.accountservice.commands.CreateAccountCommand;
import com.example.accountservice.commands.CreditMoneyCommand;
import com.example.accountservice.commands.DebitMoneyCommand;
import com.example.accountservice.entities.Account;
import com.example.accountservice.entities.dto.AccountRequest;
import com.example.accountservice.entities.dto.CreditMoneyRequest;
import com.example.accountservice.entities.dto.DebitMoneyRequest;
import com.example.accountservice.services.AccountService;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final CommandGateway commandGateway;
    private final AccountService accountService;
    public AccountController(CommandGateway commandGateway,AccountService accountService) {
        this.commandGateway = commandGateway;
        this.accountService =accountService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String createAccount(@RequestBody AccountRequest request) {
        Assert.notNull(request.getCustomerId(), "Customer id should not be null");
        String accountId = UUID.randomUUID().toString();
        commandGateway.send(new CreateAccountCommand(accountId, request.getBalance(),
                request.getCurrency(), request.getCustomerId()));
        return accountId;
    }

    @RequestMapping(value = "/credit", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public boolean creditAccount(@RequestBody CreditMoneyRequest request) {
        Assert.notNull(request.getAcccountId(), "Account id should not be null");
        commandGateway.send(new CreditMoneyCommand(request.getAcccountId(), request.getCreditAmount(),
                request.getCurrency()));
        return true;
    }

    @RequestMapping(value = "/debit", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public boolean creditAccount(@RequestBody DebitMoneyRequest request) {
        Assert.notNull(request.getAcccountId(), "Account id should not be null");
        commandGateway.send(new DebitMoneyCommand(request.getAcccountId(), request.getDebitAmount(),
                request.getCurrency()));
        return true;
    }

    @RequestMapping(value = "/getAccounts", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Iterable<Account> accounts() {
        return accountService.getAccounts();
    }

    @RequestMapping(value = "/accountById/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Account accountById(@PathVariable String id) {
        return accountService.findByAccountId(id);
    }
}
