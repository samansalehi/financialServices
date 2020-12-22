package com.example.accountservice.entity;

public enum AccountType {
    MASTER(1),VISA(2),BONE(3),CREDIT(4),DEBIT(5);
    private int value;
    AccountType(int i) {
        this.value=i;
    }
}
