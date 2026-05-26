package com.financial.FinancialTransactions.user.dto;

import com.financial.FinancialTransactions.general.enumaration.Role;
import lombok.Data;

import java.util.List;

@Data
public class UserAccountWithBankAccountsDTO {
    private String userName;
    private String firstName;
    private String lastName;
    private String password;
    private Role role;
    private String email;
    List<Long> bankAccountIds;
}
