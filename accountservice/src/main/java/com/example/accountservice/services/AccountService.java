package com.example.accountservice.services;

import com.example.accountservice.entities.Account;
import com.example.accountservice.entities.AccountStatus;
import com.example.accountservice.entities.AccountType;
import com.example.accountservice.events.*;
import com.example.accountservice.repository.AccountRepository;
import com.example.accountservice.repository.CustomerRepository;
import com.example.common.TransactionType;
import com.example.common.commands.TransactionCommand;
import com.example.common.events.TransactionEvent;
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
        log.info("AccountCreatedEvent is handled by id {}", accountCreatedEvent.id);
        Assert.notNull(accountCreatedEvent.getCustomerId(), "Customer ID should not be null");
        Assert.notNull(customerRepository.
                findByCustomerId(accountCreatedEvent.getCustomerId()), "customer_id must be excist ");
        accountRepository.save(createAccountEntity(accountCreatedEvent));
        checkBalance(accountCreatedEvent);
        log.info("Account {} is created and persisted", accountCreatedEvent.id);
    }

    @EventHandler
    public void on(AccountActivatedEvent accountActivatedEvent) {
        log.info("AccountActivatedEvent is handled by id {}", accountActivatedEvent.id);
        Account account = accountRepository.findByAccountId(accountActivatedEvent.id);
        account.setAccountStatus(AccountStatus.ACTIVE);
        accountRepository.save(account);
    }

    @EventHandler
    public void on(MoneyCreditedEvent moneyCreditedEvent) {
        log.info("MoneyCreditedEvent is handled by id {}", moneyCreditedEvent.id);
        Account account = accountRepository.findByAccountId(moneyCreditedEvent.id);
        account.setBalance(account.getBalance() + moneyCreditedEvent.getCreditAmount());
        accountRepository.save(account);
        commandGateway.send(new TransactionCommand(UUID.randomUUID().toString(),
                moneyCreditedEvent.id, moneyCreditedEvent.getCreditAmount(), account.getBalance(),
                moneyCreditedEvent.getCurrency(), TransactionType.CREDIT));
    }

    @EventHandler
    public void on(MoneyDebitedEvent moneyDebitedEvent) {
        log.info("MoneyDebitedEvent is handled by id {}", moneyDebitedEvent.id);
        Account account = accountRepository.findByAccountId(moneyDebitedEvent.id);
        account.setBalance(account.getBalance() - moneyDebitedEvent.getDebitAmount());
        accountRepository.save(account);
        commandGateway.send(new TransactionCommand(UUID.randomUUID().toString(),
                moneyDebitedEvent.id, moneyDebitedEvent.getDebitAmount(), account.getBalance(),
                moneyDebitedEvent.getCurrency(), TransactionType.DEBIT));
    }


    public void checkBalance(AccountCreatedEvent event) {
        if (event.getAccountBalance() > 0)
            log.info("TransactionCreditEvent is handled by id {}", event.id);
        commandGateway.send(new TransactionCommand(UUID.randomUUID().toString(),
                event.id, event.getAccountBalance(), event.getAccountBalance(),
                event.getCurrency(), TransactionType.CREDIT));
    }

    public Iterable<Account> getAccounts() {
        return accountRepository.findAll();
    }

    public Account findByAccountId(String id) {
        return accountRepository.findByAccountId(id);
    }

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
