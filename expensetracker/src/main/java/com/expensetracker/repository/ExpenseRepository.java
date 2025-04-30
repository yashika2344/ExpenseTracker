package com.expensetracker.repository;

import com.expensetracker.model.Expense;
import com.expensetracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // Fetch expenses by a specific user
    List<Expense> findByUser(User user);

    // Fetch expenses by a specific user within a given date range
    List<Expense> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);
}
