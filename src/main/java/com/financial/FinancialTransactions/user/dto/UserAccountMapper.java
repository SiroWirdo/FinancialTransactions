package com.financial.FinancialTransactions.user.dto;

import com.financial.FinancialTransactions.entity.UserAccount;
import org.springframework.stereotype.Component;

@Component
public class UserAccountMapper {
    public UserAccountDTO toDTO(UserAccount userAccount){
        String userName = userAccount.getUserName();
        String firstName = userAccount.getFirstName();
        String lastName = userAccount.getLastName();
        String password = userAccount.getPassword();

        return new UserAccountDTO(userName,
                firstName,
                lastName,
                password);
    }

    public UserAccount toUserAccount(UserAccountDTO userAccountDTO){
        String userName = userAccountDTO.getUserName();
        String firstName = userAccountDTO.getFirstName();
        String lastName = userAccountDTO.getLastName();
        String password = userAccountDTO.getPassword();

        return new UserAccount(userName, firstName, lastName, password);
    }
}