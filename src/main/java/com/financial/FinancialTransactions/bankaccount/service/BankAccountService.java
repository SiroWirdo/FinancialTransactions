package com.financial.FinancialTransactions.bankaccount.service;

import com.financial.FinancialTransactions.bankaccount.BankAccountRepository;
import com.financial.FinancialTransactions.bankaccount.dto.BankAccountGetDTO;
import com.financial.FinancialTransactions.bankaccount.dto.BankAccountMapper;
import com.financial.FinancialTransactions.bankaccount.dto.BankAccountWithTransactionsDTO;
import com.financial.FinancialTransactions.entity.BankAccount;
import com.financial.FinancialTransactions.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final BankAccountMapper bankAccountMapper;

    public BankAccountService(BankAccountRepository bankAccountRepository, BankAccountMapper bankAccountMapper) {
        this.bankAccountRepository = bankAccountRepository;
        this.bankAccountMapper = bankAccountMapper;
    }

    @Transactional
    public void transaction(BankAccount from, BankAccount to, BigDecimal amount) {
        from.withdraw(amount);
        to.deposit(amount);
        bankAccountRepository.save(from);
        bankAccountRepository.save(to);
    }

    public void deposit(BankAccount bankAccount, BigDecimal amount) {
        bankAccount.deposit(amount);
        bankAccountRepository.save(bankAccount);
    }

    public List<BankAccountGetDTO> getAllBankAccounts() {
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        List<BankAccountGetDTO> result = new ArrayList<>();
        bankAccounts.forEach(bankAccount -> result.add(bankAccountMapper.toBankAccountGetDTO(bankAccount)));
        return result;
    }

    public BankAccountWithTransactionsDTO getBankAccountWithTransactions(Long bankAccountId) {
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId).orElseThrow(() -> new NotFoundException("Bank Account", bankAccountId));

        return bankAccountMapper.toBankAccountWithTransactionsDTO(bankAccount);
    }
}
