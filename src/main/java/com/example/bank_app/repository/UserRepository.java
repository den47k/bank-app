package com.example.bank_app.repository;

import com.example.bank_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for performing CRUD operations on the User entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their email address.
     *
     * @param email The email address of the user
     * @return An Optional containing the user, if found
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds all users of a specific type (e.g., "user" or "organization").
     *
     * @param type The type of the user
     * @return A list of users with the specified type
     */
    List<User> findByType(String type);
}
