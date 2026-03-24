package com.financial.FinancialTransactions.user.dto;

import lombok.Getter;
import lombok.Setter;

public class UserAccountUpdateDTO {
    @Getter
    @Setter
    private String firstName;
    @Getter
    @Setter
    private String lastName;
    @Getter
    @Setter
    private String password;
}
