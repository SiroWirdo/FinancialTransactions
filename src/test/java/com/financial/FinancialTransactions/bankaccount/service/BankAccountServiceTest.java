package com.financial.FinancialTransactions.bankaccount.service;

import com.financial.FinancialTransactions.bankaccount.BankAccountRepository;
import com.financial.FinancialTransactions.bankaccount.dto.BankAccountGetDTO;
import com.financial.FinancialTransactions.bankaccount.dto.BankAccountMapper;
import com.financial.FinancialTransactions.bankaccount.dto.BankAccountWithTransactionsDTO;
import com.financial.FinancialTransactions.entity.BankAccount;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceTest {

    @Mock
    private BankAccountRepository bankAccountRepository;
    @Mock
    private BankAccountMapper bankAccountMapper;
    @InjectMocks
    private BankAccountService bankAccountService;

   /* @Test
    void createBankAccount() {

    }*/

    @Test
    void transaction() {
        BankAccount  fromBankAccount = new BankAccount();
        fromBankAccount.setBalance(BigDecimal.valueOf(1000));
        BankAccount  toBankAccount = new BankAccount();
        toBankAccount.setBalance(BigDecimal.ZERO);

        BigDecimal amount = new BigDecimal("100");

        bankAccountService.transaction(fromBankAccount,toBankAccount,amount);

        assertEquals(new BigDecimal("900"), fromBankAccount.getBalance());
        assertEquals(new BigDecimal("100"), toBankAccount.getBalance());

        verify(bankAccountRepository).save(fromBankAccount);
        verify(bankAccountRepository).save(toBankAccount);
    }

    @Test
    void deposit() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBalance(BigDecimal.valueOf(1000));

        bankAccountService.deposit(bankAccount,BigDecimal.valueOf(100));

        assertEquals(new BigDecimal("1100"), bankAccount.getBalance());
        verify(bankAccountRepository).save(bankAccount);
    }

    @Test
    void getAllBankAccounts() {
        BankAccount bankAccount = new BankAccount();
        BankAccount bankAccount2 = new BankAccount();

        ArrayList<BankAccount> bankAccounts = new ArrayList<>();
        bankAccounts.add(bankAccount);
        bankAccounts.add(bankAccount2);

        BankAccountGetDTO dto1 = new BankAccountGetDTO();
        BankAccountGetDTO dto2 = new BankAccountGetDTO();

        when(bankAccountRepository.findAll()).thenReturn(bankAccounts);
        when(bankAccountMapper.toBankAccountGetDTO(bankAccount)).thenReturn(dto1);
        when(bankAccountMapper.toBankAccountGetDTO(bankAccount2)).thenReturn(dto2);

        List<BankAccountGetDTO> result = bankAccountService.getAllBankAccounts();
        assertNotNull(result);
        assertEquals(bankAccounts.size(), result.size());
        assertEquals(dto1, result.get(0));
        assertEquals(dto2, result.get(1));

    }

    @Test
    void getBankAccountWithTransactions() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(1L);

        BankAccountWithTransactionsDTO  dto1 = new BankAccountWithTransactionsDTO();

        when(bankAccountRepository.findById(1L)).thenReturn(Optional.of(bankAccount));
        when(bankAccountMapper.toBankAccountWithTransactionsDTO(bankAccount)).thenReturn(dto1);

        BankAccountWithTransactionsDTO result = bankAccountService.getBankAccountWithTransactions(1L);

        assertNotNull(result);
        assertEquals(dto1, result);
    }
}