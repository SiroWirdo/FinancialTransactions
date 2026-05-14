package com.financial.FinancialTransactions.bankaccount.dto;

import com.financial.FinancialTransactions.transaction.dto.TransactionHistoryGetDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BankAccountWithTransactionsDTO {
    private Long bankAccountId;
    private String userName;
    private String bankAccountNumber;
    private BigDecimal balance;
    private List<TransactionHistoryGetDTO> outgoingTransactions;
    private List<TransactionHistoryGetDTO> incomingTransactions;
}
