package com.financial.FinancialTransactions.general.auth.dto;

import com.financial.FinancialTransactions.general.enumaration.Role;
import lombok.Data;

@Data
public class RegisterRequestDTO {
    private String userName;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private Role role;
}
