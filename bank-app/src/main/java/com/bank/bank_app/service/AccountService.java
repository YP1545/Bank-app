package com.bank.bank_app.service;

import com.bank.bank_app.model.Account;
import com.bank.bank_app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class AccountService {

    private List<Account> accounts = new ArrayList<>();

    private AtomicLong idCounter = new AtomicLong(1);
    @Autowired
    private TransactionService transactionService;


    @Autowired
    private UserService userService;

    public Account createAccount(Long userId, String accountType) {
    
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found: " + userId);
        }

        Account account = new Account(userId, accountType);
        account.setId(idCounter.getAndIncrement());
        accounts.add(account);
        return account;
    }

    public Account getAccount(Long accountId) {
        
        for (Account account : accounts) {
            if (account.getId().equals(accountId)) {
                return account;
            }
        }
        return null;

    }

    public Account deposit(Long accountId, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        
        Account account = getAccount(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account not found: " + accountId);
        }
        account.setBalance(account.getBalance().add(amount));
        transactionService.recordTransaction(accountId, "DEPOSIT", amount);
        return account; 
    }

    public Account withdraw(Long accountId, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }

        Account account = getAccount(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account not found: " + accountId);
        }

        if (amount.compareTo(account.getBalance()) > 0) {
            throw new IllegalArgumentException("Cannot withdraw more than balance");
        }

        account.setBalance(account.getBalance().subtract(amount));
        transactionService.recordTransaction(accountId, "WITHDRAWAL", amount);
        return account;
    }
}
 