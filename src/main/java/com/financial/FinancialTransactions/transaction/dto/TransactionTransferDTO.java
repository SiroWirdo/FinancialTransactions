package com.financial.FinancialTransactions.transaction.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionTransferDTO {
    private Long fromBankAccountId;
    private Long toBankAccountId;
    private BigDecimal amount;
    private String description;
}
