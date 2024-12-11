package com.example.bank_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Main entry point for the BankApp application.
 */
@SpringBootApplication
@EnableWebMvc
public class BankAppApplication {

	/**
	 * Main method to launch the Spring Boot application.
	 *
	 * @param args Command-line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(BankAppApplication.class, args);
	}

}

