package com.financial.FinancialTransactions.transaction.dto;

import com.financial.FinancialTransactions.general.enumaration.TransactionStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionHistoryGetDTO {
    private Long fromBankAccountId;
    private Long toBankAccountId;
    private TransactionStatus type;
    private BigDecimal amount;
    private String description;
    private LocalDateTime createdAt;
}
