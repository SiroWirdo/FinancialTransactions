package com.financial.FinancialTransactions.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(){
        super("Could not find record");
    }

    public NotFoundException(String name, Long recordId) {
        super("Could not find " + name + ", id: " + recordId);
    }

    public NotFoundException(String name, String recordCode) {
        super("Could not find " + name + ", id: " + recordCode);
    }
}
