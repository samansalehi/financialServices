package com.example.accountservice.services;

import com.example.accountservice.entities.Account;
import com.example.accountservice.entities.AccountStatus;
import com.example.accountservice.entities.AccountType;
import com.example.accountservice.events.AccountActivatedEvent;
import com.example.accountservice.events.AccountCreatedEvent;
import com.example.accountservice.events.TransactionCreditEvent;
import com.example.accountservice.repository.AccountRepository;
import com.example.accountservice.repository.CustomerRepository;
import com.example.commoncommands.TransactionCreditCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.UUID;

@Service
public class AccountService {
    static final Logger log =
            LoggerFactory.getLogger(AccountService.class);
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private CommandGateway commandGateway;

    public AccountService(CustomerRepository customerRepository,
                          AccountRepository accountRepository,
                          CommandGateway commandGateway) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.commandGateway = commandGateway;
    }

    @EventHandler
    public void on(AccountCreatedEvent accountCreatedEvent) {
        Assert.notNull(accountCreatedEvent.getCustomerId(), "Customer ID should not be null");
        Assert.notNull(customerRepository.
                findByCustomerId(accountCreatedEvent.getCustomerId()), "customer_id must be excist ");
        accountRepository.save(createAccountEntity(accountCreatedEvent));
    }

    @EventHandler
    public void on(AccountActivatedEvent accountActivatedEvent) {
        Account account = accountRepository.findByAccountId(accountActivatedEvent.id);
        account.setAccountStatus(AccountStatus.ACTIVE);
        accountRepository.save(account);
    }

    @EventHandler
    public void on(TransactionCreditEvent transactionCreditEvent) {
        commandGateway.send(new TransactionCreditCommand(UUID.randomUUID().toString(),
                transactionCreditEvent.getAccountId(), transactionCreditEvent.getCustomerId(),
                transactionCreditEvent.getAmount(), transactionCreditEvent.getCurrency()));
    }

    public Iterable<Account> getAccounts() {
        return accountRepository.findAll();
    }

    //    @EventHandler
//    public void on(AccountCreateEvent accountCreateEvent)
//    {
//        log.info("Account number {} is created.", accountCreateEvent.getAccount_id());
//    }
//    @CommandHandler
//    public void handle(ActivateAccountCommand activateAccountCommand) {
//        Assert.notNull(activateAccountCommand.getAccount_id(), "Account ID must not Be null");
//        Optional<Account> account = accountRepository.findById(activateAccountCommand.getAccount_id());
//        account.get().setAccountStatus(AccountStatus.ACTIVE);
//        accountRepository.save(account.get());
//        AggregateLifecycle.apply(new AccountActivatedEvent(account.get().getId()));
//        log.info("Account number {} is Activated.", account.get().getId());
//
//    }

    private Account createAccountEntity(AccountCreatedEvent accountCreatedEvent) {
        Account account = new Account();
        account.setAccountId(accountCreatedEvent.id);
        account.setAccountStatus(AccountStatus.OPEN);
        account.setAccountType(AccountType.MASTER);
        account.setCustomerId(accountCreatedEvent.getCustomerId());
        account.setBalance(accountCreatedEvent.getAccountBalance());
        account.setCurrency(accountCreatedEvent.getCurrency());
        return account;
    }
}
