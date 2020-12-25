package com.example.accountservice.events;


public class AccountCreateOrderEvent {
    private final long customer_id;
    private final double balance;

    public AccountCreateOrderEvent(long customer_id,double balance) {
        this.customer_id = customer_id;
        this.balance=balance;
    }

    public long getCustomer_id() {
        return customer_id;
    }



    public double getBalance() {
        return balance;
    }


}
