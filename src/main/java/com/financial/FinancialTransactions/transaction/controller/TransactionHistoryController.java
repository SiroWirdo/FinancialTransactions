package com.financial.FinancialTransactions.transaction.controller;

import com.financial.FinancialTransactions.entity.TransactionHistory;
import com.financial.FinancialTransactions.transaction.dto.TransactionHistoryGetDTO;
import com.financial.FinancialTransactions.transaction.service.TransactionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/transactions-history")
public class TransactionHistoryController {
    private TransactionHistoryService transactionHistoryService;

    @Autowired
    public TransactionHistoryController(TransactionHistoryService transactionHistoryService) {
        this.transactionHistoryService = transactionHistoryService;
    }

    @GetMapping
    public List<TransactionHistoryGetDTO> getTransactionsHistory(Long bankAccountId) {
        return transactionHistoryService.getAccountTransactionsHistory(bankAccountId);
    }
}
