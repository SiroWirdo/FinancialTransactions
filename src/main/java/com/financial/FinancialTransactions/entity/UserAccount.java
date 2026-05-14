package com.financial.FinancialTransactions.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_account_seq", allocationSize = 1)
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String userName;
    @Getter
    @Setter
    private String firstName;
    @Getter
    @Setter
    private String lastName;
    @Getter
    @Setter
    private String password;
    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL, orphanRemoval = true,  fetch = FetchType.LAZY)
    @Getter
    @Setter
    private List<BankAccount> bankAccounts = new ArrayList<>();

    public UserAccount(){
        this.userName = "";
        this.firstName = "";
        this.lastName = "";
        this.password = "";
    }

    public UserAccount(String userName, String firstName, String lastName, String password){
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public void addBankAccount(BankAccount account) {
        bankAccounts.add(account);
    }

    public void removeBankAccount(BankAccount account) {
        bankAccounts.remove(account);
        account.setUserAccount(null);
    }
}
