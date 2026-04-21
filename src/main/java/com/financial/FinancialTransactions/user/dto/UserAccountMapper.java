package com.financial.FinancialTransactions.user.dto;

import com.financial.FinancialTransactions.entity.UserAccount;
import org.springframework.stereotype.Component;

@Component
public class UserAccountMapper {
    public UserAccountGetDTO toDTO(UserAccount userAccount){
        UserAccountGetDTO userAccountGetDTO = new UserAccountGetDTO();
        userAccountGetDTO.setUserName(userAccount.getUserName());
        userAccountGetDTO.setFirstName(userAccount.getFirstName());
        userAccountGetDTO.setLastName(userAccount.getLastName());
        userAccountGetDTO.setPassword(userAccount.getPassword());

        return userAccountGetDTO;
    }

    public UserAccount toUserAccount(UserAccountGetDTO userAccountGetDTO){
        String userName = userAccountGetDTO.getUserName();
        String firstName = userAccountGetDTO.getFirstName();
        String lastName = userAccountGetDTO.getLastName();
        String password = userAccountGetDTO.getPassword();

        return new UserAccount(userName, firstName, lastName, password);
    }
}