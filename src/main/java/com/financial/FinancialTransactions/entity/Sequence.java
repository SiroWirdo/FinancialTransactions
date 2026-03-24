package com.financial.FinancialTransactions.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Sequence {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String sequenceCode;
    @Getter
    @Setter
    private String sequenceName;
    @Getter
    @Setter
    private String sequenceFormat;
    @Getter
    @Setter
    private String lastValue;
    @Getter
    @Setter
    private String nextValue;
}
