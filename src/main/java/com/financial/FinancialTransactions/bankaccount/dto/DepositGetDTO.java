package com.financial.FinancialTransactions.bankaccount.dto;

import com.financial.FinancialTransactions.general.enumaration.DepositStatus;
import com.financial.FinancialTransactions.general.enumaration.DepositType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DepositGetDTO {
    private Long id;
    private Long sourceBankAccountId;
    private BigDecimal amount;
    private DepositType depositType;
    private BigDecimal rate;
    private LocalDateTime maturityDate;
    private int lengthInMonths;
    private DepositStatus status;
}
