package com.financial.FinancialTransactions.user.dto;

import com.financial.FinancialTransactions.entity.BankAccount;
import com.financial.FinancialTransactions.entity.UserAccount;
import com.financial.FinancialTransactions.general.enumaration.Role;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserAccountMapper {
    public UserAccountDTO toDTO(UserAccount userAccount){
        UserAccountDTO userAccountDTO = new UserAccountDTO();
        userAccountDTO.setUserAccountId(userAccount.getId());
        userAccountDTO.setUserName(userAccount.getUserName());
        userAccountDTO.setFirstName(userAccount.getFirstName());
        userAccountDTO.setLastName(userAccount.getLastName());
        userAccountDTO.setPassword(userAccount.getPassword());
        userAccountDTO.setRole(userAccount.getRole());
        userAccountDTO.setEmail(userAccount.getEmail());

        return userAccountDTO;
    }

    public UserAccount toUserAccount(UserAccountDTO userAccountDTO){
        String userName = userAccountDTO.getUserName();
        String firstName = userAccountDTO.getFirstName();
        String lastName = userAccountDTO.getLastName();
        String password = userAccountDTO.getPassword();
        Role role = userAccountDTO.getRole();
        String email = userAccountDTO.getEmail();

        return new UserAccount(userName, firstName, lastName, password, role, email);
    }

    public UserAccountWithBankAccountsDTO toDTOWithBankAccounts(UserAccount userAccount){
        UserAccountWithBankAccountsDTO userAccountWithBankAccountsDTO = new UserAccountWithBankAccountsDTO();
        userAccountWithBankAccountsDTO.setUserName(userAccount.getUserName());
        userAccountWithBankAccountsDTO.setFirstName(userAccount.getFirstName());
        userAccountWithBankAccountsDTO.setLastName(userAccount.getLastName());
        userAccountWithBankAccountsDTO.setPassword(userAccount.getPassword());
        userAccountWithBankAccountsDTO.setRole(userAccount.getRole());
        userAccountWithBankAccountsDTO.setEmail(userAccount.getEmail());
        userAccountWithBankAccountsDTO.setBankAccountIds(userAccount.getBankAccounts()
                .stream()
                .map(BankAccount::getId)
                .collect(Collectors.toList())
        );

        return userAccountWithBankAccountsDTO;
    }
}