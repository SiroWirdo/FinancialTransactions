package com.financial.FinancialTransactions.entity;

import com.financial.FinancialTransactions.general.Constants;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.util.bcel.Const;

@Entity
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String bankAccountNumber;
    @Getter
    @Setter
    private double balance;

    public BankAccount() {
        this.bankAccountNumber = Constants.BANK_ID;
    }
}
