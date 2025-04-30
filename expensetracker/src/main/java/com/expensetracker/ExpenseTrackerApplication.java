package com.expensetracker;

import com.expensetracker.model.User;
import com.expensetracker.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ExpenseTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpenseTrackerApplication.class, args);
    }

    // Auto-create a demo user with weekly budget
    @Bean
    CommandLineRunner init(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("demo").isEmpty()) {
                User user = new User();
                user.setUsername("demo");

                // Encrypt password
                String rawPassword = "demo123";
                String encodedPassword = new BCryptPasswordEncoder().encode(rawPassword);
                user.setPassword(encodedPassword);

                // ✅ Set weekly budget here
                user.setWeeklyBudget(0.0);

                userRepository.save(user);
                System.out.println("Demo user created with username: demo, password: demo123, weeklyBudget: 0.0");
            } else {
                System.out.println("Demo user already exists.");
            }
        };
    }
}
