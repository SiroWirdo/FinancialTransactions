package com.financial.FinancialTransactions.entity;

import com.financial.FinancialTransactions.general.enumaration.DepositStatus;
import com.financial.FinancialTransactions.general.enumaration.DepositType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Deposit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deposit_seq")
    @SequenceGenerator(name = "deposit_seq", sequenceName = "deposit_seq", allocationSize = 1)
    @Getter
    @Setter
    private Long id;
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "source_bank_account")
    @Getter
    @Setter
    private BankAccount sourceBankAccount;
    @Getter
    @Setter
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private DepositType type;
    @Getter
    @Setter
    private BigDecimal rate;
    @Getter
    @Setter
    private LocalDateTime maturityDate;
    @Getter
    @Setter
    private int lengthInMonths;
    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private DepositStatus status;
}
