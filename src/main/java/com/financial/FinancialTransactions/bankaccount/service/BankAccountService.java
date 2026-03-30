package com.financial.FinancialTransactions.bankaccount.service;

import com.financial.FinancialTransactions.bankaccount.BankAccountRepository;
import com.financial.FinancialTransactions.entity.BankAccount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public void createBankAccount(BankAccount bankAccount) {
        bankAccountRepository.save(bankAccount);
    }

    @Transactional
    public void transaction(BankAccount from, BankAccount to, BigDecimal amount) {
        from.withdraw(amount);
        to.deposit(amount);
        bankAccountRepository.save(from);
        bankAccountRepository.save(to);
    }
}
