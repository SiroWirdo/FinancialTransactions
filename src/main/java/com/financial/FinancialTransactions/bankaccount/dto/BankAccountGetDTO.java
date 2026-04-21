package com.financial.FinancialTransactions.bankaccount.dto;

import com.financial.FinancialTransactions.entity.TransactionHistory;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BankAccountGetDTO {
    private Long bankAccountId;
    private String userName;
    private String bankAccountNumber;
    private BigDecimal balance;
}
