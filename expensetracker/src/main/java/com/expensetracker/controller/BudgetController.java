package com.expensetracker.controller;

import com.expensetracker.model.Budget;
import com.expensetracker.model.User;
import com.expensetracker.service.BudgetService;
import com.expensetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/budget")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private UserService userService;

    private User getDefaultUser() {
        return userService.findById(1L);
    }

    @GetMapping
    public String showBudgetForm(Model model) {
        User user = getDefaultUser();
        List<Budget> budgets = budgetService.getBudgetsByUser(user);
        Budget budget = budgets.isEmpty() ? new Budget() : budgets.get(0); // ✅ get existing or new

        model.addAttribute("budget", budget);
        return "budget_form";
    }

    @PostMapping("/save")
    public String saveBudget(@ModelAttribute Budget budget) {
        budget.setUser(getDefaultUser());
        budgetService.setBudget(budget); // ✅ handles both insert/update
        return "redirect:/expenses";
    }
}
