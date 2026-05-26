package com.financial.FinancialTransactions.general.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class AuthResponseDTO {
    @Getter
    private String token;
}
