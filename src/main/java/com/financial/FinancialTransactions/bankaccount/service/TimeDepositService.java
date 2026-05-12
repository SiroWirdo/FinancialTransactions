package com.financial.FinancialTransactions.bankaccount.service;

import com.financial.FinancialTransactions.bankaccount.BankAccountRepository;
import com.financial.FinancialTransactions.bankaccount.DepositRepository;
import com.financial.FinancialTransactions.bankaccount.dto.DepositGetDTO;
import com.financial.FinancialTransactions.bankaccount.dto.DepositMapper;
import com.financial.FinancialTransactions.entity.BankAccount;
import com.financial.FinancialTransactions.entity.Deposit;
import com.financial.FinancialTransactions.exception.IncorrectAmount;
import com.financial.FinancialTransactions.exception.InsufficientFundsException;
import com.financial.FinancialTransactions.exception.NotFoundException;
import com.financial.FinancialTransactions.general.enumaration.DepositStatus;
import com.financial.FinancialTransactions.general.enumaration.DepositType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TimeDepositService {
    @Autowired
    DepositRepository depositRepository;
    @Autowired
    BankAccountRepository bankAccountRepository;
    @Autowired
    DepositMapper depositMapper;


    public void openTimeDeposit(Long bankAccountId, BigDecimal amount, BigDecimal rate, int lengthInMonths) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IncorrectAmount();
        }

        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId)
                .orElseThrow(() -> new NotFoundException("BankAccount", bankAccountId));

        if (bankAccount.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException();
        }

        Deposit deposit = new Deposit();
        deposit.setSourceBankAccount(bankAccount);
        deposit.setAmount(amount);
        deposit.setRate(rate);
        deposit.setType(DepositType.TIMED);
        deposit.setStatus(DepositStatus.ACTIVE);
        deposit.setMaturityDate(LocalDateTime.now().plusMonths(lengthInMonths));

        bankAccount.withdraw(amount);

        depositRepository.save(deposit);
    }

    @Transactional
    public void closeMaturedDeposits() {
        List<Deposit> depositsToClose = depositRepository.findAllByStatusAndMaturityDateLessThanEqual(DepositStatus.ACTIVE, LocalDateTime.now());

        for(Deposit deposit : depositsToClose) {
            BigDecimal durationInYears = BigDecimal.valueOf(deposit.getLengthInMonths())
                    .divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);

            BigDecimal returnRate = deposit.getAmount()
                    .multiply(deposit.getRate())
                    .multiply(durationInYears);

            BigDecimal returnAmountAfterTax = deposit.getAmount()
                    .add(returnRate.multiply(new BigDecimal("0.81")));

            BankAccount bankAccount = deposit.getSourceBankAccount();
            bankAccount.setBalance(bankAccount.getBalance().add(returnAmountAfterTax));

            deposit.setStatus(DepositStatus.CLOSED);
            depositRepository.save(deposit);
        }
    }

    public List<DepositGetDTO> getAllDepositsForAccount(Long bankAccountId) {
        List<Deposit> deposits = depositRepository.findAllBySourceBankAccountId(bankAccountId);

        List<DepositGetDTO> dtoList = new ArrayList<>();

        deposits.forEach(deposit -> dtoList.add(depositMapper.toDepositGetDTO(deposit)));

        return dtoList;
    }
}

