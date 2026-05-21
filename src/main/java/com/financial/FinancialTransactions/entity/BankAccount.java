package com.financial.FinancialTransactions.entity;

import com.financial.FinancialTransactions.exception.IncorrectAmount;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table (name = "bank_account")
@Getter
@Setter
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_account_seq")
    @SequenceGenerator(name = "bank_account_seq", sequenceName = "bank_account_seq", allocationSize = 1)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccount userAccount;
    @Column (name = "bank_account_number", nullable = false, unique = true)
    private String bankAccountNumber;
    @Column (nullable = false, precision = 15, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;
    @OneToMany (mappedBy = "fromBankAccount", fetch = FetchType.LAZY)
    private List<TransactionHistory> outgoingTransactions;
    @OneToMany (mappedBy = "toBankAccount", fetch = FetchType.LAZY)
    private List<TransactionHistory> incomingTransactions;
    @OneToMany (mappedBy = "sourceBankAccount", fetch = FetchType.LAZY)
    private List<Deposit> deposits;

    public BankAccount() {}

    public BankAccount(UserAccount userAccount, String bankAccountNumber) {
        this.userAccount = userAccount;
        this.bankAccountNumber = bankAccountNumber;
    }

    public void deposit(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IncorrectAmount();
        }
        this.balance = this.balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IncorrectAmount();
        }

        this.balance = this.balance.subtract(amount);
    }
}
