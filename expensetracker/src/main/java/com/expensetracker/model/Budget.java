package com.expensetracker.model;

import jakarta.persistence.*;

@Entity
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;
    private double limitAmount;
    private double spent;

    @ManyToOne
    private User user;

    // No-args constructor
    public Budget() {}

    // All-args constructor
    public Budget(String category, double limitAmount, double spent, User user) {
        this.category = category;
        this.limitAmount = limitAmount;
        this.spent = spent;
        this.user = user;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public double getLimitAmount() {
        return limitAmount;
    }

    public double getSpent() {
        return spent;
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

    public void setLimitAmount(double limitAmount) {
        this.limitAmount = limitAmount;
    }

    public void setSpent(double spent) {
        this.spent = spent;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
