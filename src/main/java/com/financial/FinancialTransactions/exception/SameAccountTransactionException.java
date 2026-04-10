package com.financial.FinancialTransactions.exception;

public class SameAccountTransactionException extends RuntimeException {
    public SameAccountTransactionException() {
        super("Cannot transfer to the same account");
    }
}
