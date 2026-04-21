package com.financial.FinancialTransactions.entity;

import com.financial.FinancialTransactions.general.enumaration.TransactionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction_history")
public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_hist_seq")
    @SequenceGenerator(name = "transaction_hist_seq", sequenceName = "transaction_hist_seq", allocationSize = 1)
    private Long id;
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "from_bank_account_id")
    @Getter
    @Setter
    private BankAccount fromBankAccount;
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "to_bank_account_id")
    @Getter
    @Setter
    private BankAccount toBankAccount;
    @Getter
    @Setter
    private BigDecimal amount;
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private TransactionStatus type;
    @Getter
    @Setter
    private LocalDateTime createdAt;
    @Getter
    @Setter
    private String description;

    public TransactionHistory() {}

    public TransactionHistory(BankAccount fromBankAccount,
                              BankAccount toBankAccount,
                              BigDecimal amount,
                              TransactionStatus type,
                              LocalDateTime createdAt,
                              String description) {
        this.fromBankAccount = fromBankAccount;
        this.toBankAccount = toBankAccount;
        this.amount = amount;
        this.type = type;
        this.createdAt = createdAt;
        this.description = description;
    }
}
