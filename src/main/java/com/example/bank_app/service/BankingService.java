package com.example.bank_app.service;

import com.example.bank_app.model.Transaction;
import com.example.bank_app.model.User;
import com.example.bank_app.repository.TransactionRepository;
import com.example.bank_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service class that handles banking operations.
 *
 * This class provides methods for transferring funds, paying bills,
 * and fetching transaction histories. It interacts with the User and Transaction
 * repositories to update user balances and store transaction records.
 */
@Service
@RequiredArgsConstructor
public class BankingService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    /**
     * Transfers funds from the sender to the recipient.
     *
     * @param sender The user initiating the transfer
     * @param recipientId The ID of the recipient
     * @param amount The amount to transfer
     * @throws IllegalArgumentException If the sender has insufficient funds or the recipient is not found
     */
    public void transferFunds(User sender, Long recipientId, BigDecimal amount) {
        if (sender.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        User recipient = userRepository.findById(recipientId)
                .orElseThrow(() -> new IllegalArgumentException("Recipient not found"));

        sender.setBalance(sender.getBalance().subtract(amount));
        recipient.setBalance(recipient.getBalance().add(amount));

        userRepository.save(sender);
        userRepository.save(recipient);

        Transaction transaction = new Transaction();
        transaction.setUser(sender);
        transaction.setAmount(amount);
        transaction.setType("TRANSFER");
        transaction.setDescription("Transfer to " + recipient.getName());
        transactionRepository.save(transaction);
    }

    /**
     * Pays a bill from the user to a biller.
     *
     * @param user The user making the payment
     * @param amount The amount to pay
     * @param biller The user receiving the payment
     * @throws IllegalArgumentException If the user has insufficient funds
     */
    public void payBill(User user, BigDecimal amount, User biller) {
        if (user.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        user.setBalance(user.getBalance().subtract(amount));
        biller.setBalance(biller.getBalance().add(amount));
        userRepository.save(user);

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setAmount(amount);
        transaction.setType("BILL_PAYMENT");
        transaction.setDescription("Bill payment to " + biller.getName());
        transactionRepository.save(transaction);
    }
}
