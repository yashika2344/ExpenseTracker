package com.expensetracker.service.Impl;

import com.expensetracker.model.Budget;
import com.expensetracker.model.User;
import com.expensetracker.repository.BudgetRepository;
import com.expensetracker.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BudgetServiceImpl implements BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Override
    public Budget setBudget(Budget budget) {
        List<Budget> existingBudgets = budgetRepository.findByUser(budget.getUser());
        if (!existingBudgets.isEmpty()) {
            Budget existing = existingBudgets.get(0);
            existing.setCategory(budget.getCategory());
            existing.setLimitAmount(budget.getLimitAmount());
            return budgetRepository.save(existing); // ✅ Update existing
        }
        return budgetRepository.save(budget); // ✅ Insert if new
    }

    @Override
    public Budget updateBudget(Long id, Budget updatedBudget) {
        Optional<Budget> existing = budgetRepository.findById(id);
        if (existing.isPresent()) {
            Budget b = existing.get();
            b.setCategory(updatedBudget.getCategory());
            b.setLimitAmount(updatedBudget.getLimitAmount());
            b.setSpent(updatedBudget.getSpent());
            b.setUser(updatedBudget.getUser());
            return budgetRepository.save(b);
        }
        return null;
    }

    @Override
    public Budget getBudgetById(Long id) {
        return budgetRepository.findById(id).orElse(null);
    }

    @Override
    public double getWeeklyBudgetByUser(User user) {
        List<Budget> budgets = budgetRepository.findByUser(user);
        if (budgets != null && !budgets.isEmpty()) {
            return budgets.get(0).getLimitAmount();
        }
        return 0.0;
    }

    // ✅ Add this for form pre-fill
    @Override
    public List<Budget> getBudgetsByUser(User user) {
        return budgetRepository.findByUser(user);
    }
}
