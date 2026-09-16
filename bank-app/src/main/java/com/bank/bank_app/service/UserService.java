package com.bank.bank_app.service;

import com.bank.bank_app.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {

    private List<User> users = new ArrayList<>();

    private AtomicLong idCounter = new AtomicLong(1);

    public User createUser(User user) {
        user.setId(idCounter.getAndIncrement());
        
        users.add(user);
        return user;
    }

    // Return all users
    public List<User> getAllUsers() {
        
        return users;
    }
    
    public User deleteUser(Long id) {
        User user = getUserById(id);
        if (user == null) {
            throw new IllegalArgumentException("User not found: " + id);
        }
        users.remove(user);
        return user;
    }

    public User updateUser(Long id, User updatedUser) {
        User existingUser = getUserById(id);
        if (existingUser == null) {
            throw new IllegalArgumentException("User not found: " + id);
        }
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        return existingUser;
    }
    public User getUserById(Long id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }
}