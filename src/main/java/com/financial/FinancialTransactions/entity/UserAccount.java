package com.financial.FinancialTransactions.entity;

import com.financial.FinancialTransactions.general.enumaration.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "user_account")
@Getter
@Setter
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_account_seq", allocationSize = 1)
    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String password;
    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL, orphanRemoval = true,  fetch = FetchType.LAZY)
    private List<BankAccount> bankAccounts;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String email;

    public UserAccount() {
        this.bankAccounts = new ArrayList<>();
    }

    public UserAccount(String userName, String firstName, String lastName, String password, Role role, String email) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
        this.email = email;
        this.bankAccounts = new ArrayList<>();
    }

    public void addBankAccount(BankAccount account) {
        bankAccounts.add(account);
    }

    /*public void removeBankAccount(BankAccount account) {
        bankAccounts.remove(account);
        account.setUserAccount(null);
    }*/
}
