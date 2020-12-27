package com.example.accountservice.entities.dto;

import com.example.common.Currency;

public class CreditMoneyRequest {
    private double creditAmount;
    private String acccountId;
    private Currency currency;

    public double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(double creditAmount) {
        this.creditAmount = creditAmount;
    }

    public String getAcccountId() {
        return acccountId;
    }

    public void setAcccountId(String acccountId) {
        this.acccountId = acccountId;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
