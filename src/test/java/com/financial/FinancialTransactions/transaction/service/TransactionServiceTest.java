package com.financial.FinancialTransactions.transaction.service;

import com.financial.FinancialTransactions.bankaccount.BankAccountRepository;
import com.financial.FinancialTransactions.bankaccount.service.BankAccountService;
import com.financial.FinancialTransactions.entity.BankAccount;
import com.financial.FinancialTransactions.transaction.TransactionHistoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    TransactionHistoryRepository transactionHistoryRepository;
    @Mock
    BankAccountService bankAccountService;
    @Mock
    BankAccountRepository bankAccountRepository;
    @InjectMocks
    TransactionService transactionService;

    @Test
    void transfer() {
        var fromBankAccountId = 1L;
        var fromBankAccount = new BankAccount();
        fromBankAccount.setId(fromBankAccountId);
        fromBankAccount.setBalance(new BigDecimal("100"));
        var toBankAccountId = 2L;
        var toBankAccount = new BankAccount();
        toBankAccount.setId(toBankAccountId);
        toBankAccount.setBalance(new BigDecimal("200"));

        var amount = new BigDecimal("100");

        when(bankAccountRepository.findByIdForUpdate(fromBankAccountId)).thenReturn(Optional.of(fromBankAccount));
        when(bankAccountRepository.findByIdForUpdate(toBankAccountId)).thenReturn(Optional.of(toBankAccount));

        transactionService.transfer(fromBankAccountId, toBankAccountId, amount, "test");

        verify(bankAccountService).transaction(fromBankAccount, toBankAccount, amount);
        verify(transactionHistoryRepository).save(any());

    }

    @Test
    void deposit() {
        var bankAccountId = 1L;
        var bankAccount = new BankAccount();
        bankAccount.setId(bankAccountId);
        bankAccount.setBalance(new BigDecimal("100"));
        var amount = new BigDecimal("100");

        when(bankAccountRepository.findByIdForUpdate(bankAccountId)).thenReturn(Optional.of(bankAccount));

        transactionService.deposit(bankAccountId, amount);

        verify(bankAccountService).deposit(bankAccount, amount);
    }
}