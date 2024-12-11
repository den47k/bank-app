package com.example.bank_app.controller;

import com.example.bank_app.model.User;
import com.example.bank_app.repository.UserRepository;
import com.example.bank_app.service.BankingService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Controller for managing home page-related operations.
 */
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final BankingService bankingService;
    private final UserRepository userRepository;

    /**
     * Handles the GET request for the home page.
     *
     * @param session the current HTTP session containing user data.
     * @param model   the model to pass attributes to the view.
     * @return the name of the home page view or redirects to login if the user is not logged in.
     */
    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        User sessionUser = (User) session.getAttribute("user");

        if (sessionUser == null) {
            return "redirect:/login";
        }

        User freshUser = userRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        session.setAttribute("user", freshUser);

        List<User> billers = userRepository.findByType("organization");

        model.addAttribute("billers", billers);
        model.addAttribute("transactions", freshUser.getTransactions());
        model.addAttribute("user", freshUser);
        return "home";
    }
}
