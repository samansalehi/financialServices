package com.example.accountservice.aggregate;

import com.example.accountservice.commands.ActivateAccountCommand;
import com.example.accountservice.commands.RegisterAccountCommand;
import com.example.accountservice.commands.TransactionCreateCommand;
import com.example.accountservice.entity.Account;
import com.example.accountservice.entity.AccountStatus;
import com.example.accountservice.entity.AccountType;
import com.example.accountservice.events.AccountActivatedEvent;
import com.example.accountservice.events.AccountCreateEvent;
import com.example.accountservice.repository.AccountRepository;
import com.example.accountservice.repository.CustomerRepository;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.Optional;


@Aggregate
public class AccountAggregate {
    static final Logger log =
            LoggerFactory.getLogger(AccountAggregate.class);
    @Autowired
    private CustomerRepository customerRepository;
    private AccountRepository accountRepository;

    @CommandHandler
    public void createAccount(RegisterAccountCommand registerAccountCommand) {
        Assert.notNull(registerAccountCommand.getCustomer_id(), "Customer ID should not be null");
        Assert.notNull(customerRepository.
                findById(registerAccountCommand.getCustomer_id()), "customer_id must be excist ");
        Account account = accountRepository.save(createAccountEntity(registerAccountCommand));
        AggregateLifecycle.apply(new AccountCreateEvent(registerAccountCommand.getCustomer_id(),
                account.getId(), registerAccountCommand.getBalance()));
        log.info("Account number {} is created.", account.getId());
    }

    @CommandHandler
    public void activateAccount(ActivateAccountCommand activateAccountCommand) {
        Assert.notNull(activateAccountCommand.getAccount_id(), "Account ID must not Be null");
        Optional<Account> account = accountRepository.findById(activateAccountCommand.getAccount_id());
        account.get().setAccountStatus(AccountStatus.ACTIVE);
        accountRepository.save(account.get());
        AggregateLifecycle.apply(new AccountActivatedEvent(account.get().getId()));
        log.info("Account number {} is Activated.", account.get().getId());

    }

    @EventSourcingHandler
    public void createAccount(AccountCreateEvent accountCreateEvent) {
        if (accountCreateEvent.getBalance() > 0) {
            AggregateLifecycle.apply(new TransactionCreateCommand(accountCreateEvent.getAccount_id(),
                    accountCreateEvent.getBalance()));
            log.info("balance is {} so create Transaction and send to Transaction Service",
                    accountCreateEvent.getBalance());
        }
    }

    private Account createAccountEntity(RegisterAccountCommand registerAccountCommand) {
        Account account = new Account();
        account.setAccountStatus(AccountStatus.OPEN);
        account.setAccountType(AccountType.MASTER);
        account.setCustomer_id(registerAccountCommand.getCustomer_id());
        account.setBalance(registerAccountCommand.getBalance());
        return account;
    }
}
