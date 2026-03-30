package com.financial.FinancialTransactions.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

@Entity
public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_hist_seq")
    @SequenceGenerator(name = "transaction_seq", sequenceName = "transaction_hist_seq", allocationSize = 1)
    private Long id;

}
