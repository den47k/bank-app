package com.example.bank_app.controller;

import com.example.bank_app.model.User;
import com.example.bank_app.service.BankingService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;

/**
 * Controller for managing banking-related operations such as transfers and bill payments.
 */
@Controller
@RequiredArgsConstructor
public class BankingController {

    private final BankingService bankingService;

    /**
     * Handles POST requests to transfer funds between users.
     *
     * @param recipientId the ID of the recipient user.
     * @param amount the amount to be transferred.
     * @param session the current HTTP session containing user data.
     * @param model the model to pass attributes to the view.
     * @return the name of the home page view or redirects to login if the user is not logged in.
     */
    @PostMapping("/transfer")
    public String transferFunds(@ModelAttribute("recipientId") Long recipientId,
                                @ModelAttribute("amount") BigDecimal amount,
                                HttpSession session, org.springframework.ui.Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        try {
            bankingService.transferFunds(user, recipientId, amount);
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "home";
        }
    }

    /**
     * Handles POST requests to pay bills.
     *
     * @param amount the amount to be paid.
     * @param biller the biller to whom the payment is being made.
     * @param session the current HTTP session containing user data.
     * @param model the model to pass attributes to the view.
     * @return the name of the home page view or redirects to login if the user is not logged in.
     */
    @PostMapping("/pay-bill")
    public String payBill(@ModelAttribute("amount") BigDecimal amount,
                          @ModelAttribute("biller") User biller,
                          HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        try {
            bankingService.payBill(user, amount, biller);
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "home";
        }
    }
}
