package com.bank.bank_app.model;

import java.math.BigDecimal;

public class Account {
    private Long id;
    private Long userId;
    private BigDecimal balance;
    private String accountType;

    public Account() {
    }

    public Account(Long userId, String accountType) {
        this.userId = userId;
        this.accountType = accountType;
        this.balance = BigDecimal.ZERO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}