package com.financial.FinancialTransactions.transaction.controller;

import com.financial.FinancialTransactions.transaction.dto.TransactionHistoryDTO;
import com.financial.FinancialTransactions.transaction.service.TransactionHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/transactions-history")
public class TransactionHistoryController {
    private final TransactionHistoryService transactionHistoryService;

    public TransactionHistoryController(TransactionHistoryService transactionHistoryService) {
        this.transactionHistoryService = transactionHistoryService;
    }

    @Operation(summary = "Retrieve all historical transactions for given bank account")
    @GetMapping
    public List<TransactionHistoryDTO> getTransactionsHistory(@RequestParam Long bankAccountId) {
        return transactionHistoryService.getAccountTransactionsHistory(bankAccountId);
    }
}
