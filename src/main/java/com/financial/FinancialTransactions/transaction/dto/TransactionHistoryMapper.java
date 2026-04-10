package com.financial.FinancialTransactions.transaction.dto;

import com.financial.FinancialTransactions.entity.TransactionHistory;
import org.springframework.stereotype.Component;

@Component
public class TransactionHistoryMapper {
    public TransactionHistoryGetDTO toTransactionHistoryDTO(TransactionHistory transactionHistory) {
        TransactionHistoryGetDTO dto = new TransactionHistoryGetDTO();
        dto.setFromBankAccountId(transactionHistory.getFromBankAccount().getId());
        dto.setToBankAccountId(transactionHistory.getToBankAccount().getId());
        dto.setType(transactionHistory.getType());
        dto.setAmount(transactionHistory.getAmount());
        dto.setDescription(transactionHistory.getDescription());
        dto.setCreatedAt(transactionHistory.getCreatedAt());

        return dto;
    }
}
