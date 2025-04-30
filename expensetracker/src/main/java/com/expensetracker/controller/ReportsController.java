package com.expensetracker.controller;

import com.expensetracker.model.User;
import com.expensetracker.service.ExpenseService;
import com.expensetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@RequestMapping("/reports")
public class ReportsController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserService userService;

    private User getDefaultUser() {
        return userService.findById(1L);
    }

    @GetMapping
    public String showReports(Model model) {
        User user = getDefaultUser();
        LocalDate today = LocalDate.now();
        LocalDate weekStart = today.minusDays(7);

        // Get expenses for the last week
        var weeklyExpenses = expenseService.getExpensesForWeek(user, weekStart, today);
        
        // Calculate total expenses for the week
        double weeklyTotal = weeklyExpenses.stream()
            .mapToDouble(expense -> expense.getAmount())
            .sum();

        model.addAttribute("weeklyExpenses", weeklyExpenses);
        model.addAttribute("weeklyTotal", weeklyTotal);
        model.addAttribute("weekStart", weekStart);
        model.addAttribute("weekEnd", today);

        return "reports";
    }
}