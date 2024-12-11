package com.example.bank_app.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Entity class representing a user in the system.
 */
@Data
@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)  // Ensure column length
    private String email;

    @Column(nullable = false, length = 255)  // Ensure column length
    private String name;

    @Column(length = 255)  // Optional: Only if you want to specify max length
    private String organizationName;

    @Column(nullable = false, length = 255)  // Ensure column length
    private String password;

    @Column(nullable = false, length = 255)  // Ensure column length
    private String type; // For user type (e.g., "user", "organization")

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;
}
