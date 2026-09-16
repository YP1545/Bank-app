package com.bank.bank_app.service;

import com.bank.bank_app.model.Account;
import com.bank.bank_app.model.User;
import com.bank.bank_app.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    private UserService userService;
    private TransactionService transactionService;

    public AccountService(AccountRepository accountRepository, UserService userService, TransactionService transactionService) {
        this.accountRepository = accountRepository;
        this.userService = userService;
        this.transactionService = transactionService;
        userService.setAccountService(this);
    }

    public Account createAccount(String userId, String accountType) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found: " + userId);
        }

        Account account = new Account(userId, accountType);
        return accountRepository.save(account);
    }

    public Account deleteAccount(String accountId) {
        Account account = getAccount(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account not found: " + accountId);
        }
        accountRepository.deleteById(accountId);
        return account;
    }

    public void deleteAccountsByUserId(String userId) {
        var accounts = accountRepository.findByUserId(userId);
        accounts.forEach(account -> {
            account.setBalance(BigDecimal.ZERO);
            accountRepository.save(account);
        });
        accountRepository.deleteAll(accounts);
    }

    public Account getAccount(String accountId) {
        return accountRepository.findById(accountId).orElse(null);
    }

    public Account deposit(String accountId, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }

        Account account = getAccount(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account not found: " + accountId);
        }
        account.setBalance(account.getBalance().add(amount));
        Account saved = accountRepository.save(account);
        transactionService.recordTransaction(accountId, "DEPOSIT", amount);
        return saved;
    }

    public Account withdraw(String accountId, BigDecimal amount) {
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
        Account saved = accountRepository.save(account);
        transactionService.recordTransaction(accountId, "WITHDRAW", amount);
        return saved;
    }
}