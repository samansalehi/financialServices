package com.example.accountservice.controller;

import com.example.accountservice.commands.RegisterAccountCommand;
import com.example.accountservice.entities.AccountStatus;
import com.example.accountservice.entities.AccountType;
import com.example.accountservice.entities.dto.AccountRequest;
import com.example.accountservice.entities.dto.AccountResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public AccountController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @RequestMapping(value = "/create",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public AccountResponse createAccount(@RequestBody AccountRequest request) {
        commandGateway.send(new RegisterAccountCommand(request.getCustomer_id(),request.getBalance()))
        return new AccountResponse(1, AccountStatus.OPEN, AccountType.CREDIT);
    }
}
