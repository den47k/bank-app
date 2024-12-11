package com.example.bank_app.service;

import com.example.bank_app.model.User;
import com.example.bank_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Service class that handles user authentication and registration.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    /**
     * Registers a new user.
     *
     * @param user The user to be registered
     * @return The registered user object
     * @throws IllegalArgumentException If the email already exists
     */
    public User registerUser(User user) {
        userRepository.findByEmail(user.getEmail()).ifPresent(existingUser -> {
            throw new IllegalArgumentException("Email already exists");
        });

        return userRepository.save(user);
    }

    /**
     * Logs in a user by validating their email and password.
     *
     * @param email The email of the user
     * @param password The password of the user
     * @return The authenticated user object
     * @throws IllegalArgumentException If the credentials are invalid
     */
    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!Objects.equals(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        return user;
    }
}
