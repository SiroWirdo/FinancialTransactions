package com.financial.FinancialTransactions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FinancialTransactionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinancialTransactionsApplication.class, args);
	}

}
