package com.bank.bank_app.service;

import com.bank.bank_app.model.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private List<Transaction> transactions = new ArrayList<>();
    private AtomicLong idCounter = new AtomicLong(1);

    public Transaction recordTransaction(Long accountId, String type, BigDecimal amount) {
        Transaction txn = new Transaction(accountId, type, amount);
        txn.setId(idCounter.getAndIncrement());
        transactions.add(txn);
        return txn;
    }

    public List<Transaction> getTransactionsForAccount(Long accountId) {
        return transactions.stream()
                .filter(t -> t.getAccountId().equals(accountId))
                .collect(Collectors.toList());
    }
}