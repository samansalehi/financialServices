package com.example.accountservice.entities.dto;

import com.example.common.Currency;

public class DebitMoneyRequest {
    private double debitAmount;
    private String acccountId;
    private Currency currency;

    public double getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(double debitAmount) {
        this.debitAmount = debitAmount;
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
