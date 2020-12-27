package com.example.accountservice.entities;

public enum AccountStatus {
    CREATE(0), OPEN(1), CLOSED(2), ACTIVE(3), PENDING(4);
    private int value;

    AccountStatus(int i) {
        this.value = i;
    }
}
