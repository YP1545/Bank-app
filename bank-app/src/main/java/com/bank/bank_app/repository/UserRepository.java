package com.bank.bank_app.repository;

import com.bank.bank_app.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}