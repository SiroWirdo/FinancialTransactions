package com.financial.FinancialTransactions.entity;

import com.financial.FinancialTransactions.general.enumaration.DepositStatus;
import com.financial.FinancialTransactions.general.enumaration.DepositType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table (name = "deposit")
@Getter
@Setter
public class Deposit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deposit_seq")
    @SequenceGenerator(name = "deposit_seq", sequenceName = "deposit_seq", allocationSize = 1)
    private Long id;
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "source_bank_account")
    private BankAccount sourceBankAccount;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private DepositType type;
    private BigDecimal rate;
    private LocalDateTime maturityDate;
    private int lengthInMonths;
    @Enumerated(EnumType.STRING)
    private DepositStatus status;
}
