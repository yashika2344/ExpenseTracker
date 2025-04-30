package com.expensetracker;

import com.expensetracker.model.Budget;
import com.expensetracker.model.User;
import com.expensetracker.repository.BudgetRepository;
import com.expensetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        User user = userRepository.findById(1L).orElse(null);
        if (user != null && budgetRepository.findByUser(user) == null) {
            Budget budget = new Budget();
            budget.setCategory("Weekly");
            budget.setLimitAmount(1000.0);
            budget.setSpent(0.0);
            budget.setUser(user);
            budgetRepository.save(budget);
            System.out.println("✅ Default budget inserted for user ID 1");
        }
    }
}
