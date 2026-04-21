package com.financial.FinancialTransactions.transaction.service;

import com.financial.FinancialTransactions.bankaccount.BankAccountRepository;
import com.financial.FinancialTransactions.bankaccount.service.BankAccountService;
import com.financial.FinancialTransactions.entity.BankAccount;
import com.financial.FinancialTransactions.entity.TransactionHistory;
import com.financial.FinancialTransactions.exception.IncorrectAmount;
import com.financial.FinancialTransactions.exception.InsufficientFundsException;
import com.financial.FinancialTransactions.exception.NotFoundException;
import com.financial.FinancialTransactions.exception.SameAccountTransactionException;
import com.financial.FinancialTransactions.general.enumaration.TransactionStatus;
import com.financial.FinancialTransactions.transaction.TransactionHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionService {
    private final BankAccountRepository bankAccountRepository;
    private final BankAccountService bankAccountService;
    private final TransactionHistoryRepository transactionHistoryRepository;
    private final TransactionHistoryService transactionHistoryService;

    public TransactionService(BankAccountRepository bankAccountRepository,
                              BankAccountService bankAccountService,
                              TransactionHistoryRepository transactionHistoryRepository,
                              TransactionHistoryService transactionHistoryService) {
        this.bankAccountRepository = bankAccountRepository;
        this.bankAccountService = bankAccountService;
        this.transactionHistoryRepository = transactionHistoryRepository;
        this.transactionHistoryService = transactionHistoryService;
    }

    @Transactional
    public void transfer(Long fromBankAccountId, Long toBankAccountId, BigDecimal amount, String description) {
        if (fromBankAccountId.equals(toBankAccountId)) {
            throw new SameAccountTransactionException();
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IncorrectAmount();
        }

        BankAccount first;
        BankAccount second;

        /* To avoid deadlock always lock records in the same order */
        if (fromBankAccountId < toBankAccountId) {
            first = bankAccountRepository.findByIdForUpdate(fromBankAccountId)
                    .orElseThrow(() -> new NotFoundException("BankAccount", fromBankAccountId));
            second = bankAccountRepository.findByIdForUpdate(toBankAccountId)
                    .orElseThrow(() -> new NotFoundException("BankAccount", toBankAccountId));
        } else {
            first = bankAccountRepository.findByIdForUpdate(toBankAccountId)
                    .orElseThrow(() -> new NotFoundException("BankAccount", toBankAccountId));
            second = bankAccountRepository.findByIdForUpdate(fromBankAccountId)
                    .orElseThrow(() -> new NotFoundException("BankAccount", fromBankAccountId));
        }

        BankAccount fromBankAccount = first.getId().equals(fromBankAccountId) ? first : second;
        BankAccount toBankAccount = first.getId().equals(toBankAccountId) ? first : second;

        TransactionHistory transactionHistory = new TransactionHistory(fromBankAccount,
                toBankAccount,
                amount,
                TransactionStatus.PENDING,
                LocalDateTime.now(),
                description);

        if (fromBankAccount.getBalance().compareTo(amount) < 0) {
            transactionHistoryService.saveFailed(transactionHistory);
            throw new InsufficientFundsException();
        }

        bankAccountService.transaction(fromBankAccount, toBankAccount, amount);

        transactionHistory.setType(TransactionStatus.COMPLETED);
        transactionHistoryRepository.save(transactionHistory);
    }

    @Transactional
    public void deposit(Long bankAccountId, BigDecimal amount) {
        BankAccount bankAccount = bankAccountRepository.findByIdForUpdate(bankAccountId)
                .orElseThrow(() -> new NotFoundException("BankAccount", bankAccountId));

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IncorrectAmount();
        }

        bankAccountService.deposit(bankAccount, amount);
    }
}
