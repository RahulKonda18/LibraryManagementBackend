package com.rahulkonda.LibraryManagementBackend.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String email;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal walletBalance;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal totalFinesPaid;
    
    public enum UserRole {
        ADMIN, SUBSCRIBER
    }
    
    public User() {
        this.walletBalance = BigDecimal.valueOf(200.00); // Initial wallet amount
        this.totalFinesPaid = BigDecimal.ZERO;
    }
    
    public User(String username, String password, String name, String email, UserRole role) {
        this();
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = role;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public UserRole getRole() {
        return role;
    }
    
    public void setRole(UserRole role) {
        this.role = role;
    }
    
    public BigDecimal getWalletBalance() {
        return walletBalance;
    }
    
    public void setWalletBalance(BigDecimal walletBalance) {
        this.walletBalance = walletBalance;
    }
    
    public BigDecimal getTotalFinesPaid() {
        return totalFinesPaid;
    }
    
    public void setTotalFinesPaid(BigDecimal totalFinesPaid) {
        this.totalFinesPaid = totalFinesPaid;
    }
}
