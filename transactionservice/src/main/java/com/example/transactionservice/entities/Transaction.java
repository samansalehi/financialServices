package com.example.transactionservice.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TRANSACTION")
public class Transaction {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "fromAccount_id")
    private long fromAccount_id;

    @Column(name = "toAccount_id")
    private long toAccount_id;

    @Column(name = "amount")
    private double amount;

    @Column(name = "date")
    private Date date;

    @Enumerated(EnumType.STRING)
    TransactionType transactionType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFromAccount_id() {
        return fromAccount_id;
    }

    public void setFromAccount_id(long fromAccount_id) {
        this.fromAccount_id = fromAccount_id;
    }

    public long getToAccount_id() {
        return toAccount_id;
    }

    public void setToAccount_id(long toAccount_id) {
        this.toAccount_id = toAccount_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}
