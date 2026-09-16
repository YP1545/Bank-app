package com.bank.bank_app.service;

import com.bank.bank_app.model.Transaction;
import com.bank.bank_app.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {

    private TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction recordTransaction(String accountId, String type, BigDecimal amount) {
        Transaction txn = new Transaction(accountId, type, amount);
        return transactionRepository.save(txn);
    }

    public List<Transaction> getTransactionsForAccount(String accountId) {
        return transactionRepository.findByAccountId(accountId);
    }
}