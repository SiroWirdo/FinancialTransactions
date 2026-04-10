package com.financial.FinancialTransactions.transaction.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionDepositDTO {
    private Long bankAccountId;
    private BigDecimal amount;
}
