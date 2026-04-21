package com.financial.FinancialTransactions.bankaccount.controller;

import com.financial.FinancialTransactions.bankaccount.dto.BankAccountGetDTO;
import com.financial.FinancialTransactions.bankaccount.dto.BankAccountWithTransactionsDTO;
import com.financial.FinancialTransactions.bankaccount.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public List<BankAccountGetDTO> getBankAccounts() {
        return bankAccountService.getAllBankAccounts();
    }

    @GetMapping ("/bank-account")
    public BankAccountWithTransactionsDTO getBankAccountWithTransactions(@RequestParam Long bankAccountId) {
        return bankAccountService.getBankAccountWithTransactions(bankAccountId);
    }
}
