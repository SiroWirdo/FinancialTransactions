package com.financial.FinancialTransactions.bankaccount.service;

import com.financial.FinancialTransactions.bankaccount.BankAccountRepository;
import com.financial.FinancialTransactions.bankaccount.DepositRepository;
import com.financial.FinancialTransactions.bankaccount.dto.DepositDTO;
import com.financial.FinancialTransactions.bankaccount.dto.DepositMapper;
import com.financial.FinancialTransactions.entity.BankAccount;
import com.financial.FinancialTransactions.entity.Deposit;
import com.financial.FinancialTransactions.exception.IncorrectAmount;
import com.financial.FinancialTransactions.exception.InsufficientFundsException;
import com.financial.FinancialTransactions.general.enumaration.DepositStatus;
import com.financial.FinancialTransactions.general.enumaration.DepositType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TimeDepositServiceTest {
    @Mock
    DepositRepository depositRepository;
    @Mock
    BankAccountRepository bankAccountRepository;
    @Mock
    DepositMapper depositMapper;
    @InjectMocks
    TimeDepositService timeDepositService;

    @Test
    void openTimeDeposit() {
        Long bankAccountId = 1L;
        BigDecimal amount = new BigDecimal(1000);
        BigDecimal rate = new BigDecimal("0.5");
        int lengthInMonths = 6;

        BankAccount bankAccount = new BankAccount();
        bankAccount.setBalance(BigDecimal.valueOf(5000));

        when(bankAccountRepository.findById(bankAccountId)).thenReturn(Optional.of(bankAccount));

        timeDepositService.openTimeDeposit(bankAccountId, amount, rate, lengthInMonths);

        ArgumentCaptor<Deposit> depositCaptor = ArgumentCaptor.forClass(Deposit.class);

        verify(depositRepository).save(depositCaptor.capture());

        Deposit savedDeposit = depositCaptor.getValue();

        assertEquals(0, savedDeposit.getAmount().compareTo(amount));
        assertEquals(0, savedDeposit.getRate().compareTo(rate));

        assertEquals(DepositType.TIMED, savedDeposit.getType());
        assertEquals(DepositStatus.ACTIVE, savedDeposit.getStatus());

        assertEquals(bankAccount, savedDeposit.getSourceBankAccount());

        assertEquals(BigDecimal.valueOf(4000), bankAccount.getBalance());

    }

    @Test
    void openDepositIncorrectAmountException() {
        Long bankAccountId = 1L;
        BigDecimal amount = BigDecimal.ZERO;
        BigDecimal rate = new BigDecimal("0.5");
        int lengthInMonths = 6;

        assertThrows(IncorrectAmount.class,
                () -> timeDepositService.openTimeDeposit(bankAccountId, amount, rate, lengthInMonths)
        );
    }

    @Test
    void openDepositInsufficientFundsException() {
        Long bankAccountId = 1L;
        BigDecimal amount = BigDecimal.valueOf(1000);
        BigDecimal rate = new BigDecimal("0.5");
        int lengthInMonths = 6;

        BankAccount bankAccount = new BankAccount();
        bankAccount.setBalance(BigDecimal.valueOf(500));

        when(bankAccountRepository.findById(bankAccountId)).thenReturn(Optional.of(bankAccount));

        assertThrows(InsufficientFundsException.class,
                () ->  timeDepositService.openTimeDeposit(bankAccountId, amount, rate, lengthInMonths));
    }

    @Test
    void closeMaturedDeposits() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBalance(BigDecimal.valueOf(0));

        Deposit depositToClose =  new Deposit();
        depositToClose.setAmount(BigDecimal.valueOf(5000));
        depositToClose.setRate(new BigDecimal("0.5"));
        depositToClose.setStatus(DepositStatus.ACTIVE);
        depositToClose.setSourceBankAccount(bankAccount);
        depositToClose.setLengthInMonths(6);

        when(depositRepository.findAllByStatusAndMaturityDateLessThanEqual(eq(DepositStatus.ACTIVE), any(LocalDateTime.class)))
                .thenReturn(List.of(depositToClose));

        timeDepositService.closeMaturedDeposits();

        verify(depositRepository).save(depositToClose);

        assertEquals(DepositStatus.CLOSED, depositToClose.getStatus());

        assertEquals(0, bankAccount.getBalance().compareTo(BigDecimal.valueOf(6012.5)));
    }

    @Test
    void getAllDepositsForAccount() {
        Long bankaccountId = 1L;
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(bankaccountId);

        Deposit deposit = new Deposit();
        deposit.setSourceBankAccount(bankAccount);

        DepositDTO dto1 = new DepositDTO();

        when(depositRepository.findAllBySourceBankAccountId(bankaccountId)).thenReturn(List.of(deposit));
        when(depositMapper.toDepositGetDTO(deposit)).thenReturn(dto1);

        List<DepositDTO> depositList = timeDepositService.getAllDepositsForAccount(bankaccountId);

        assertFalse(depositList.isEmpty());
        assertEquals(1, depositList.size());
        assertEquals(dto1, depositList.getFirst());
    }
}