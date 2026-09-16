package com.bank.bank_app.service;

import com.bank.bank_app.model.Account;
import com.bank.bank_app.model.User;
import com.bank.bank_app.repository.AccountRepository;
import com.bank.bank_app.repository.TransactionRepository;
import com.bank.bank_app.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    private UserService userService;
    private TransactionService transactionService;
    private AccountService accountService;
    private UserRepository userRepository;
    private AccountRepository accountRepository;
    private String testUserId;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        accountRepository = mock(AccountRepository.class);
        TransactionRepository transactionRepository = mock(TransactionRepository.class);
        transactionService = new TransactionService(transactionRepository);
        userService = new UserService(userRepository);
        accountService = new AccountService(accountRepository, userService, transactionService);

        User user = new User("Test User", "test@example.com");
        user.setId("user-1");
        when(userRepository.findById("user-1")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> {
            Account account = invocation.getArgument(0);
            if (account.getId() == null) {
                account.setId("account-1");
            }
            return account;
        });
        User savedUser = userService.createUser(user);
        testUserId = savedUser.getId();
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
            accountService.createAccount("missing-user", "SAVINGS");
        });
    }

    @Test
    void deposit_increasesBalance() {
        Account account = accountService.createAccount(testUserId, "SAVINGS");
        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));

        accountService.deposit(account.getId(), new BigDecimal("500"));

        Account updated = accountService.getAccount(account.getId());
        assertEquals(new BigDecimal("500"), updated.getBalance());
    }

    @Test
    void deposit_throwsWhenAmountIsNegative() {
        Account account = accountService.createAccount(testUserId, "SAVINGS");
        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));

        assertThrows(IllegalArgumentException.class, () -> {
            accountService.deposit(account.getId(), new BigDecimal("-50"));
        });
    }

    @Test
    void withdraw_decreasesBalance() {
        Account account = accountService.createAccount(testUserId, "SAVINGS");
        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));
        accountService.deposit(account.getId(), new BigDecimal("500"));

        accountService.withdraw(account.getId(), new BigDecimal("200"));

        Account updated = accountService.getAccount(account.getId());
        assertEquals(new BigDecimal("300"), updated.getBalance());
    }

    @Test
    void withdraw_throwsWhenAmountExceedsBalance() {
        Account account = accountService.createAccount(testUserId, "SAVINGS");
        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));
        accountService.deposit(account.getId(), new BigDecimal("100"));

        assertThrows(IllegalArgumentException.class, () -> {
            accountService.withdraw(account.getId(), new BigDecimal("500"));
        });
    }

    @Test
    void deleteUser_removesConnectedAccountAndResetsBalance() {
        Account account = accountService.createAccount(testUserId, "SAVINGS");
        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));
        accountService.deposit(account.getId(), new BigDecimal("500"));

        when(accountRepository.findByUserId(testUserId)).thenReturn(List.of(account));
        clearInvocations(accountRepository);

        User deletedUser = userService.DeleteUserById(testUserId);

        assertNotNull(deletedUser);
        assertEquals(BigDecimal.ZERO, account.getBalance());
        verify(accountRepository).save(account);
        verify(accountRepository).deleteAll(List.of(account));
        verify(userRepository).deleteById(testUserId);
    }
}
