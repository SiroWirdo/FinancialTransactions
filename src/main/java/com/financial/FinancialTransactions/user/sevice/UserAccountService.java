package com.financial.FinancialTransactions.user.sevice;

import com.financial.FinancialTransactions.entity.UserAccount;
import com.financial.FinancialTransactions.exception.NotFoundException;
import com.financial.FinancialTransactions.sequence.service.IbanGeneratorService;
import com.financial.FinancialTransactions.user.UserAccountRepository;
import com.financial.FinancialTransactions.user.dto.UserAccountDTO;
import com.financial.FinancialTransactions.user.dto.UserAccountMapper;
import com.financial.FinancialTransactions.user.dto.UserAccountWithBankAccountsDTO;
import com.financial.FinancialTransactions.user.dto.UserAccountWithoutUserNameDTO;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final UserAccountMapper userAccountMapper;

    public UserAccountService(UserAccountRepository userAccountRepository, UserAccountMapper userAccountMapper, IbanGeneratorService ibanGeneratorService) {
        this.userAccountRepository = userAccountRepository;
        this.userAccountMapper = userAccountMapper;
    }

    public List<UserAccountDTO> getAllUserAccounts() {
        return userAccountRepository.findAll()
                .stream()
                .map(userAccountMapper::toDTO)
                .collect(toList());
    }

    public UserAccountDTO updateUserAccount(Long userId, UserAccountWithoutUserNameDTO dto) {
        UserAccount userAccount = userAccountRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("UserAccount", userId));

        if (dto.getFirstName() != null) {userAccount.setFirstName(dto.getFirstName());}
        if (dto.getLastName() != null) {userAccount.setLastName(dto.getLastName());}
        if (dto.getPassword() != null) {userAccount.setPassword(dto.getPassword());}
        if (dto.getRole() != null) {userAccount.setRole(dto.getRole());}
        if (dto.getEmail() != null) {userAccount.setEmail(dto.getEmail());}

        UserAccount result = userAccountRepository.save(userAccount);

        return userAccountMapper.toDTO(result);
    }

    public UserAccountWithBankAccountsDTO getUserAccountWithBankAccounts(Long userAccountId) {
        UserAccount userAccount = userAccountRepository.findById(userAccountId)
                .orElseThrow(() -> new NotFoundException("User Account", userAccountId));

        return userAccountMapper.toDTOWithBankAccounts(userAccount);
    }
}
