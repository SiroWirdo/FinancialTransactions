package com.financial.FinancialTransactions.user.dto;

import com.financial.FinancialTransactions.general.enumaration.Role;
import lombok.Data;

@Data
public class UserAccountWithoutUserNameDTO {
    private String firstName;
    private String lastName;
    private String password;
    private Role role;
    private String email;
}
