package com.financial.FinancialTransactions.user.dto;

import com.financial.FinancialTransactions.entity.UserAccount;
import com.financial.FinancialTransactions.general.enumaration.Role;
import org.springframework.stereotype.Component;

@Component
public class UserAccountMapper {
    public UserAccountGetDTO toDTO(UserAccount userAccount){
        UserAccountGetDTO userAccountGetDTO = new UserAccountGetDTO();
        userAccountGetDTO.setUserName(userAccount.getUserName());
        userAccountGetDTO.setFirstName(userAccount.getFirstName());
        userAccountGetDTO.setLastName(userAccount.getLastName());
        userAccountGetDTO.setPassword(userAccount.getPassword());
        userAccountGetDTO.setRole(userAccount.getRole());
        userAccountGetDTO.setEmail(userAccount.getEmail());

        return userAccountGetDTO;
    }

    public UserAccount toUserAccount(UserAccountGetDTO userAccountGetDTO){
        String userName = userAccountGetDTO.getUserName();
        String firstName = userAccountGetDTO.getFirstName();
        String lastName = userAccountGetDTO.getLastName();
        String password = userAccountGetDTO.getPassword();
        Role role = userAccountGetDTO.getRole();
        String email = userAccountGetDTO.getEmail();

        return new UserAccount(userName, firstName, lastName, password, role, email);
    }
}