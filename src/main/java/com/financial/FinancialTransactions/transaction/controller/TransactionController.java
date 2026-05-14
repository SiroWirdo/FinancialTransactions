package com.financial.FinancialTransactions.transaction.controller;

import com.financial.FinancialTransactions.transaction.dto.TransactionDepositDTO;
import com.financial.FinancialTransactions.transaction.dto.TransactionTransferDTO;
import com.financial.FinancialTransactions.transaction.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(@RequestBody TransactionTransferDTO request) {

        transactionService.transfer(
                request.getFromBankAccountId(),
                request.getToBankAccountId(),
                request.getAmount(),
                request.getDescription()
        );

        return ResponseEntity.ok().build();
    }

    @PostMapping("/deposit")
    public ResponseEntity<Void> deposit(@RequestBody TransactionDepositDTO request) {
        transactionService.deposit(request.getBankAccountId(), request.getAmount());
        return ResponseEntity.ok().build();
    }
}
