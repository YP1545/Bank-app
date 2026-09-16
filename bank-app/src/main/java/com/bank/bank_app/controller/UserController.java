package com.bank.bank_app.controller;

import com.bank.bank_app.model.User;
import com.bank.bank_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserController {

    
    private @Autowired UserService userService;
    
    @PostMapping 
    public User createUser( @RequestBody User user) {

        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser( @PathVariable String id,  @RequestBody User updatedUser) {
        return userService.updateUser(id, updatedUser);
    }

    @DeleteMapping("/{id}")
    public User deleteUser( @PathVariable String id) {
        return userService.DeleteUserById(id);
    }
    
   
    @GetMapping 
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById( @PathVariable String id) {
        User user = userService.getUserById(id);
        return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }
}