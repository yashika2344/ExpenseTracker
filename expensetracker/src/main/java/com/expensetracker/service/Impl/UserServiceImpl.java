package com.expensetracker.service.Impl;

import com.expensetracker.model.User;
import com.expensetracker.repository.UserRepository;
import com.expensetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User register(User user) {
        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repo.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return repo.findByUsername(username).orElse(null);
    }

    @Override
    public User findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public boolean authenticate(String username, String password) {
        User user = findByUsername(username);
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }
}
