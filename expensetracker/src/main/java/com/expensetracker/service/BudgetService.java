package com.expensetracker.service;

import com.expensetracker.model.Budget;
import com.expensetracker.model.User;

import java.util.List;

public interface BudgetService {
    Budget setBudget(Budget budget);
    Budget updateBudget(Long id, Budget budget);
    Budget getBudgetById(Long id);

    double getWeeklyBudgetByUser(User user); // ✅ For displaying alerts

    List<Budget> getBudgetsByUser(User user); // ✅ For loading into budget form
}
