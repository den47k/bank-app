package com.example.bank_app.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity class representing a transaction in the system.
 */
@Data
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // User initiating the transaction

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private String type; // "TRANSFER", "BILL_PAYMENT", etc.

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();
}
