package com.financial.FinancialTransactions.bankaccount.service;

import com.financial.FinancialTransactions.bankaccount.BankAccountRepository;
import com.financial.FinancialTransactions.bankaccount.dto.BankAccountDTO;
import com.financial.FinancialTransactions.bankaccount.dto.BankAccountMapper;
import com.financial.FinancialTransactions.bankaccount.dto.BankAccountWithTransactionsDTO;
import com.financial.FinancialTransactions.entity.BankAccount;
import com.financial.FinancialTransactions.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final BankAccountMapper bankAccountMapper;

    public BankAccountService(BankAccountRepository bankAccountRepository, BankAccountMapper bankAccountMapper) {
        this.bankAccountRepository = bankAccountRepository;
        this.bankAccountMapper = bankAccountMapper;
    }

    @Transactional
    public void transaction(BankAccount fromBankAccount, BankAccount toBankAccount, BigDecimal amount) {
        fromBankAccount.withdraw(amount);
        toBankAccount.deposit(amount);
        bankAccountRepository.save(fromBankAccount);
        bankAccountRepository.save(toBankAccount);
    }

    public void deposit(BankAccount bankAccount, BigDecimal amount) {
        bankAccount.deposit(amount);
        bankAccountRepository.save(bankAccount);
    }

    public List<BankAccountDTO> getAllBankAccounts() {
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();

        return bankAccounts.stream()
                .map(bankAccountMapper::toBankAccountGetDTO)
                .collect(Collectors.toList());
    }

    public BankAccountWithTransactionsDTO getBankAccountWithTransactions(Long bankAccountId) {
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId).orElseThrow(() -> new NotFoundException("Bank Account", bankAccountId));

        return bankAccountMapper.toBankAccountWithTransactionsDTO(bankAccount);
    }
}
