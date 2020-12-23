package com.example.accountservice.entities;

public enum MediaStatus {
    OPEN(1), CLOSED(2), ACTIVE(3), PENDING(4),EXPIRED(5);
    private int value;

    MediaStatus(int i) {
        this.value=i;
    }
}
