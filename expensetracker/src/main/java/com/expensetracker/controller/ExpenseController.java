package com.expensetracker.controller;

import com.expensetracker.model.Expense;
import com.expensetracker.model.User;
import com.expensetracker.service.ExpenseService;
import com.expensetracker.service.UserService;
import com.expensetracker.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserService userService;

    @Autowired
    private BudgetService budgetService;

    // Utility method to get a default user (no login required)
    private User getDefaultUser() {
        return userService.findById(1L); // assumes user with ID 1 exists
    }

    @GetMapping
    public String listExpenses(Model model) {
        User user = getDefaultUser();
        List<Expense> expenses = expenseService.getExpensesByUser(user);
        double totalSpent = expenses.stream().mapToDouble(Expense::getAmount).sum(); // Total spent

        double weeklyBudget = budgetService.getWeeklyBudgetByUser(user); // Fetch weekly budget
        boolean budgetExceeded = totalSpent > weeklyBudget; // Check if exceeded

        model.addAttribute("expenses", expenses);
        model.addAttribute("budgetExceeded", budgetExceeded); // Message condition
        model.addAttribute("weeklyBudget", weeklyBudget);
        model.addAttribute("totalSpent", totalSpent);

        return "expenses"; // Renders expenses.html
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("expense", new Expense());
        return "add_expense";
    }

    @PostMapping("/save")
    public String saveExpense(@ModelAttribute Expense expense) {
        expenseService.addExpense(expense, getDefaultUser());
        return "redirect:/expenses";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Expense expense = expenseService.getExpenseById(id);
        model.addAttribute("expense", expense);
        return "edit_expense";
    }

    @PostMapping("/update")
    public String updateExpense(@ModelAttribute Expense expense) {
        expenseService.addExpense(expense, getDefaultUser());
        return "redirect:/expenses";
    }

    @GetMapping("/delete/{id}")
    public String deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return "redirect:/expenses";
    }

    // New method to generate weekly report
    @GetMapping("/weekly-report")
    public String getWeeklyReport(@RequestParam("startDate") String startDateStr,
                                   @RequestParam("endDate") String endDateStr, Model model) {
        // Parse the start and end dates
        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);

        User user = getDefaultUser(); // Get the current user (you may want to change this logic)

        // Get expenses for the week
        List<Expense> expenses = expenseService.getExpensesForWeek(user, startDate, endDate);

        // Group expenses by category and sum the amounts
        Map<String, Double> categoryTotals = expenses.stream()
            .collect(Collectors.groupingBy(Expense::getCategory, 
                      Collectors.summingDouble(Expense::getAmount)));

        // Add data to the model
        model.addAttribute("categoryTotals", categoryTotals);
        model.addAttribute("totalExpense", categoryTotals.values().stream().mapToDouble(Double::doubleValue).sum());

        return "weekly_report"; // Return the weekly report view
    }
}
