package com.expensetracker.service;

import com.expensetracker.model.User;

public interface UserService {

    // Register a new user, encrypting the password before saving
    User register(User user);

    // Find a user by their username
    User findByUsername(String username);

    // Find a user by their ID (used when bypassing login)
    User findById(Long id);

    // ✅ Authenticate user by username and password
    boolean authenticate(String username, String password);
}
