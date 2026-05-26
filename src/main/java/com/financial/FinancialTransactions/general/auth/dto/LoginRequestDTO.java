package com.financial.FinancialTransactions.general.auth.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {

    private String email;
    private String password;
}

