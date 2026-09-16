package com.bank.bank_app.controller;

import com.bank.bank_app.model.Transaction;
import com.bank.bank_app.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> recordTransaction(
            @RequestParam String accountId,
            @RequestParam String type,
            @RequestParam BigDecimal amount) {
        Transaction transaction = transactionService.recordTransaction(accountId, type, amount);
        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Transaction>> getTransactionsForAccount(@PathVariable String accountId) {
        List<Transaction> transactions = transactionService.getTransactionsForAccount(accountId);
        return ResponseEntity.ok(transactions);
    }
}
