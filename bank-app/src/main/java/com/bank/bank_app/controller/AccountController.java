package com.bank.bank_app.controller;

import com.bank.bank_app.model.Account;
import com.bank.bank_app.model.Transaction;
import com.bank.bank_app.model.User;
import com.bank.bank_app.service.AccountService;
import com.bank.bank_app.service.TransactionService;
import com.bank.bank_app.service.UserService;
import org.springframework.security.core.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @GetMapping("/{id}/transactions")
    public List<Transaction> getTransactions(@PathVariable String id) {
        return transactionService.getTransactionsForAccount(id);
    }

    @PostMapping
    public Account createAccount(@RequestBody CreateAccountRequest request) {
        return accountService.createAccount(request.userId, request.accountType);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getAccount(@PathVariable String id) {
        Account account = accountService.getAccount(id);
        if (account == null) {
            return ResponseEntity.notFound().build();
        }

        User owner = userService.getUserById(account.getUserId());
        String ownerName = (owner != null) ? owner.getName() : "Unknown";

        Map<String, Object> response = Map.of(
                "id", account.getId(),
                "userId", account.getUserId(),
                "userName", ownerName,
                "balance", account.getBalance(),
                "accountType", account.getAccountType()
        );

        return ResponseEntity.ok(response);
    }
    
    
    @GetMapping("/my")
    public List<Account> getMyAccounts(Authentication authentication) {
        String email = authentication.getName();
        User user = userService.getUserByEmail(email);
        return accountService.getAccountsByUserId(user.getId());
    }

    @PostMapping("/{id}/deposit")
    public Account deposit(@PathVariable String id, @RequestBody AmountRequest request) {
        return accountService.deposit(id, request.amount);
    }

    @PostMapping("/{id}/withdraw")
    public Account withdraw(@PathVariable String id, @RequestBody AmountRequest request) {
        return accountService.withdraw(id, request.amount);
    }

    @PostMapping("/{id}/delete")
    public Account deleteAccount(@PathVariable String id) {
        return accountService.deleteAccount(id);
    }

    public static class CreateAccountRequest {
        public String userId;
        public String accountType;
    }

    public static class AmountRequest {
        public BigDecimal amount;
    }
    
}