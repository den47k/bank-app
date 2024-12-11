package com.example.bank_app.repository;

import com.example.bank_app.model.Transaction;
import com.example.bank_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for performing CRUD operations on the Transaction entity.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Finds all transactions associated with a specific user.
     *
     * @param user The user whose transactions are to be fetched
     * @return A list of transactions for the specified user
     */
    List<Transaction> findByUser(User user);
}
