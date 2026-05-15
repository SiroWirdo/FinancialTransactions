package com.financial.FinancialTransactions.bankaccount.controller;

import com.financial.FinancialTransactions.bankaccount.dto.BankAccountGetDTO;
import com.financial.FinancialTransactions.bankaccount.dto.BankAccountWithTransactionsDTO;
import com.financial.FinancialTransactions.bankaccount.service.BankAccountService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bank-accounts")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @Operation(summary = "Retrieve all bank accounts")
    @GetMapping
    public List<BankAccountGetDTO> getBankAccounts() {
        return bankAccountService.getAllBankAccounts();
    }

    @Operation(summary = "Retrieve given bank account with its transaction history")
    @GetMapping ("/bank-account")
    public BankAccountWithTransactionsDTO getBankAccountWithTransactions(@RequestParam Long bankAccountId) {
        return bankAccountService.getBankAccountWithTransactions(bankAccountId);
    }
}
