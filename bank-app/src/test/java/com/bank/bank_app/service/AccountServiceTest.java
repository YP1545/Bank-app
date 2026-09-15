package com.bank.bank_app.service;

import com.bank.bank_app.model.Account;
import com.bank.bank_app.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    private UserService userService;
    private TransactionService transactionService;
    private AccountService accountService;
    private Long testUserId;

    @BeforeEach
    void setUp() {
        // Fresh, real instances before every test — no mocking needed since
        // these are simple in-memory services, not external systems.
        userService = new UserService();
        transactionService = new TransactionService();
        accountService = new AccountService(userService, transactionService);

        User user = userService.createUser(new User(null, "Test User", "test@example.com"));
        testUserId = user.getId();
    }

    @Test
    void createAccount_setsInitialBalanceToZero() {
        Account account = accountService.createAccount(testUserId, "SAVINGS");

        assertEquals(BigDecimal.ZERO, account.getBalance());
        assertEquals("SAVINGS", account.getAccountType());
        assertNotNull(account.getId());
    }

    @Test
    void createAccount_throwsWhenUserDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> {
            accountService.createAccount(999L, "SAVINGS");
        });
    }

    @Test
    void deposit_increasesBalance() {
        Account account = accountService.createAccount(testUserId, "SAVINGS");

        accountService.deposit(account.getId(), new BigDecimal("500"));

        Account updated = accountService.getAccount(account.getId());
        assertEquals(new BigDecimal("500"), updated.getBalance());
    }

    @Test
    void deposit_throwsWhenAmountIsNegative() {
        Account account = accountService.createAccount(testUserId, "SAVINGS");

        assertThrows(IllegalArgumentException.class, () -> {
            accountService.deposit(account.getId(), new BigDecimal("-50"));
        });
    }

    @Test
    void withdraw_decreasesBalance() {
        Account account = accountService.createAccount(testUserId, "SAVINGS");
        accountService.deposit(account.getId(), new BigDecimal("500"));

        accountService.withdraw(account.getId(), new BigDecimal("200"));

        Account updated = accountService.getAccount(account.getId());
        assertEquals(new BigDecimal("300"), updated.getBalance());
    }

    @Test
    void withdraw_throwsWhenAmountExceedsBalance() {
        Account account = accountService.createAccount(testUserId, "SAVINGS");
        accountService.deposit(account.getId(), new BigDecimal("100"));

        assertThrows(IllegalArgumentException.class, () -> {
            accountService.withdraw(account.getId(), new BigDecimal("500"));
        });
    }
}
