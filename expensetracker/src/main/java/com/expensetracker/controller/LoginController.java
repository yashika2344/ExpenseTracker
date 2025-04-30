package com.expensetracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login"; // This will load login.html from templates
    }

    @GetMapping("/")
    public String homeRedirect() {
        return "redirect:/expenses"; // After login, redirect to expenses
    }
}
