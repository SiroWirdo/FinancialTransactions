package com.financial.FinancialTransactions.bankaccount.dto;

import com.financial.FinancialTransactions.general.enumaration.DepositType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositOpenNewDTO {

    private Long sourceBankAccountId;
    private BigDecimal amount;
    private DepositType depositType;
    private BigDecimal rate;
    private int lengthInMonths;
}
