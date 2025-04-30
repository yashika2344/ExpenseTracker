package com.expensetracker.service;

import com.expensetracker.model.Expense;
import com.expensetracker.model.User;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseService {

    // Save or update an expense for a specific user
    Expense addExpense(Expense expense, User user);

    // Fetch all expenses for all users (useful for admin or testing)
    List<Expense> getAllExpenses();

    // Fetch expenses belonging to a particular user
    List<Expense> getExpensesByUser(User user);

    // Fetch an expense by ID (used for edit form)
    Expense getExpenseById(Long id);

    // Delete an expense by ID
    void deleteExpense(Long id);

    // Fetch expenses for a specific user within a given week (between start and end dates)
    List<Expense> getExpensesForWeek(User user, LocalDate startDate, LocalDate endDate);
}
