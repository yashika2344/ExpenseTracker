package com.expensetracker.config;

import com.expensetracker.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Disable CSRF for simplicity, but you can configure it if needed
            .authorizeHttpRequests()
                .requestMatchers("/login", "/register", "/css/**", "/js/**").permitAll() // Allow unauthenticated access to these paths
                .anyRequest().authenticated() // Require authentication for other paths
            .and()
            .formLogin()
                .loginPage("/login") // Define the custom login page
                .defaultSuccessUrl("/expenses", true) // Redirect to the expenses page after successful login
                .permitAll() // Allow all users to access the login page
            .and()
            .logout()
                .logoutUrl("/logout") // URL to trigger logout (by default this is /logout, so you can leave it as is)
                .logoutSuccessUrl("/login?logout") // Redirect to login page with a logout parameter after logout
                .permitAll(); // Allow everyone to access the logout URL

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Use BCrypt for password encryption
    }

    // Register custom user details service for authentication
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                   .userDetailsService(userDetailsService) // Use custom user details service
                   .passwordEncoder(passwordEncoder()) // Use BCrypt for password encoding
                   .and()
                   .build();
    }
}
