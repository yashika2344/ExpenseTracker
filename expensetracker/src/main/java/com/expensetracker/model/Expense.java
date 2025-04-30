package com.expensetracker.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;
    private double amount;
    private String description;
    private LocalDate date;

    @ManyToOne
    private User user;

    // No-args constructor
    public Expense() {}

    // All-args constructor
    public Expense(String category, double amount, String description, LocalDate date, User user) {
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.user = user;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
