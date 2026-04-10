package com.financial.FinancialTransactions.exception;

public class IncorrectAmount extends RuntimeException {
    public IncorrectAmount() {
        super("Amount must be greater than 0.");
    }
}
