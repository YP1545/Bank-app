package com.bank.bank_app.service;

import com.bank.bank_app.model.User;
import com.bank.bank_app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private AccountService accountService;

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public void setAccountService(AccountService accountService) {
    this.accountService = accountService;
}
    public User updateUser(String id, User updatedUser) {
        User existing = userRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }
        existing.setName(updatedUser.getName());
        existing.setEmail(updatedUser.getEmail());
        return userRepository.save(existing);
    }

    public User DeleteUserById(String id) {
    User existing = userRepository.findById(id).orElse(null);
    if (existing == null) {
        return null;
    }
    if (accountService != null) {
        accountService.deleteAccountsByUserId(id);
    }
    userRepository.deleteById(id);
    return existing;
}
}