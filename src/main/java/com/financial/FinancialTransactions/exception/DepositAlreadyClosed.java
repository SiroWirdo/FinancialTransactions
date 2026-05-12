package com.financial.FinancialTransactions.exception;

public class DepositAlreadyClosed extends RuntimeException {
    public DepositAlreadyClosed() {
        super("Deposit already closed");
    }
}
