package com.financial.FinancialTransactions.general.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class AuthResponse {
    @Getter
    private String token;
}
