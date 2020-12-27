package com.example.accountservice.entities.dto;

import com.example.accountservice.entities.AccountStatus;
import com.example.accountservice.entities.AccountType;


public class AccountResponse {


    public AccountResponse(long account_id, AccountStatus accountStatus, AccountType accountType) {
        this.account_id = account_id;
        this.accountStatus = accountStatus;
        this.accountType = accountType;
    }

    private long account_id;
    private AccountStatus accountStatus;
    private AccountType accountType;

    public long getAccount_id() {
        return account_id;
    }

    public void setAccount_id(long account_id) {
        this.account_id = account_id;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
}
