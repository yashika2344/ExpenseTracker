package com.expensetracker.service.Impl;

import com.expensetracker.model.Expense;
import com.expensetracker.model.User;
import com.expensetracker.repository.ExpenseRepository;
import com.expensetracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Override
    public Expense addExpense(Expense expense, User user) {
        expense.setUser(user); // Set the user for this expense
        return expenseRepository.save(expense); // Used for both add & update
    }

    @Override
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    @Override
    public List<Expense> getExpensesByUser(User user) {
        return expenseRepository.findByUser(user);
    }

    @Override
    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    @Override
    public List<Expense> getExpensesForWeek(User user, LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findByUserAndDateBetween(user, startDate, endDate);
    }
}
