package com.financial.FinancialTransactions.bankaccount.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BankAccountGetDTO {
    private Long bankAccountId;
    private String userName;
    private String bankAccountNumber;
    private BigDecimal balance;
}
