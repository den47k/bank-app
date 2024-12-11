package com.example.bank_app.controller;

import com.example.bank_app.model.User;
import com.example.bank_app.service.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller for managing authentication and user registration.
 */
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Displays the registration form.
     *
     * @param model the model to pass attributes to the view.
     * @return the name of the registration view.
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    /**
     * Handles user registration.
     *
     * @param user the user object containing registration details.
     * @param session the current HTTP session.
     * @return redirects to the home page on success or back to the registration page on error.
     */
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, HttpSession session) {
        try {
            // Register the user
            User registeredUser = authService.registerUser(user);
            // Save the registered user in the session
            session.setAttribute("user", registeredUser);

            return "redirect:/";
        } catch (IllegalArgumentException e) {
            return "redirect:/register?error=" + e.getMessage();
        }
    }

    /**
     * Displays the login form.
     *
     * @return the name of the login view.
     */
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    /**
     * Handles user login.
     *
     * @param email the email address of the user.
     * @param password the password of the user.
     * @param session the current HTTP session.
     * @param model the model to pass attributes to the view.
     * @return redirects to the home page on success or back to the login page on error.
     */
    @PostMapping("/login")
    public String loginUser(@ModelAttribute("email") String email,
                            @ModelAttribute("password") String password,
                            HttpSession session,
                            Model model) {
        try {
            // Authenticate the user
            User authenticatedUser = authService.loginUser(email, password);
            // Save the authenticated user in the session
            session.setAttribute("user", authenticatedUser);
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }
}
