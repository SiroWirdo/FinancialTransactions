package com.financial.FinancialTransactions.transaction.controller;

import com.financial.FinancialTransactions.transaction.dto.TransactionHistoryGetDTO;
import com.financial.FinancialTransactions.transaction.service.TransactionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions-history")
public class TransactionHistoryController {
    private final TransactionHistoryService transactionHistoryService;

    public TransactionHistoryController(TransactionHistoryService transactionHistoryService) {
        this.transactionHistoryService = transactionHistoryService;
    }

    @GetMapping
    public List<TransactionHistoryGetDTO> getTransactionsHistory(@RequestParam Long bankAccountId) {
        return transactionHistoryService.getAccountTransactionsHistory(bankAccountId);
    }
}
