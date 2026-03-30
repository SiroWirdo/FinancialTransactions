package com.financial.FinancialTransactions.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_account_seq")
    @SequenceGenerator(name = "bank_seq", sequenceName = "bank_account_seq", allocationSize = 1)
    @Getter
    @Setter
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Getter
    @Setter
    private UserAccount userAccount;
    @Getter
    @Column(name = "bank_account_number", nullable = false, unique = true)
    private String bankAccountNumber;
    @Getter
    @Setter
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;
    @OneToMany
    private List<TransactionHistory> transactionsHistory;

    public BankAccount() {}

    public BankAccount(UserAccount userAccount, String bankAccountNumber) {
        this.userAccount = userAccount;
        this.bankAccountNumber = bankAccountNumber;
    }

    public void deposit(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        this.balance = this.balance.subtract(amount);
    }
}
