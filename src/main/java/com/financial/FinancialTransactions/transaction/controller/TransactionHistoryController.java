package com.financial.FinancialTransactions.transaction.controller;

import com.financial.FinancialTransactions.transaction.dto.TransactionHistoryGetDTO;
import com.financial.FinancialTransactions.transaction.service.TransactionHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions-history")
public class TransactionHistoryController {
    private final TransactionHistoryService transactionHistoryService;

    public TransactionHistoryController(TransactionHistoryService transactionHistoryService) {
        this.transactionHistoryService = transactionHistoryService;
    }

    @Operation(summary = "Retrieve all historical transactions for given bank account")
    @GetMapping
    public List<TransactionHistoryGetDTO> getTransactionsHistory(@RequestParam Long bankAccountId) {
        return transactionHistoryService.getAccountTransactionsHistory(bankAccountId);
    }
}
