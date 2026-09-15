package com.bank.bank_app.controller;

import com.bank.bank_app.model.Account;
import com.bank.bank_app.service.AccountService;
import com.bank.bank_app.service.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.bank.bank_app.model.Transaction;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;
    
    
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/{id}/transactions")
    public List<Transaction> getTransactions(@PathVariable Long id) {
        return transactionService.getTransactionsForAccount(id);
    }

    @PostMapping
    public Account createAccount(@RequestBody CreateAccountRequest request) {
        return accountService.createAccount(request.userId, request.accountType);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable Long id) {
        Account account = accountService.getAccount(id);
        return account == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(account);
    }

    @PostMapping("/{id}/deposit")
    public Account deposit(@PathVariable Long id, @RequestBody AmountRequest request) {
        return accountService.deposit(id, request.amount);
    }

    @PostMapping("/{id}/withdraw")
    public Account withdraw(@PathVariable Long id, @RequestBody AmountRequest request) {
        return accountService.withdraw(id, request.amount);
    }

    public static class CreateAccountRequest {
        public Long userId;
        public String accountType;
    }

    public static class AmountRequest {
        public BigDecimal amount;
    }
}