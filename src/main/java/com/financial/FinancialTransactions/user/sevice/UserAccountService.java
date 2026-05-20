package com.financial.FinancialTransactions.user.sevice;

import com.financial.FinancialTransactions.entity.BankAccount;
import com.financial.FinancialTransactions.entity.UserAccount;
import com.financial.FinancialTransactions.exception.NotFoundException;
import com.financial.FinancialTransactions.sequence.service.IbanGeneratorService;
import com.financial.FinancialTransactions.user.UserAccountRepository;
import com.financial.FinancialTransactions.user.dto.UserAccountGetDTO;
import com.financial.FinancialTransactions.user.dto.UserAccountMapper;
import com.financial.FinancialTransactions.user.dto.UserAccountUpdateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final UserAccountMapper userAccountMapper;
    private final IbanGeneratorService ibanGeneratorService;

    public UserAccountService(UserAccountRepository userAccountRepository, UserAccountMapper userAccountMapper, IbanGeneratorService ibanGeneratorService) {
        this.userAccountRepository = userAccountRepository;
        this.userAccountMapper = userAccountMapper;
        this.ibanGeneratorService = ibanGeneratorService;
    }

    public List<UserAccountGetDTO> getAllUserAccounts() {
        return userAccountRepository.findAll()
                .stream()
                .map(userAccountMapper::toDTO)
                .collect(toList());
    }

    @Transactional
    public UserAccountGetDTO createUserAccount(UserAccountGetDTO userAccountGetDTO) {
        String iban = ibanGeneratorService.generateIBAN();
        UserAccount userAccount = userAccountMapper.toUserAccount(userAccountGetDTO);
        BankAccount bankAccount = new BankAccount(userAccount, iban);
        userAccount.addBankAccount(bankAccount);
        UserAccount result = userAccountRepository.save(userAccount);
        return userAccountMapper.toDTO(result);
    }

    public UserAccountGetDTO createAdminAccount (UserAccountGetDTO userAccountGetDTO) {
        UserAccount userAccount = userAccountMapper.toUserAccount(userAccountGetDTO);
        UserAccount result = userAccountRepository.save(userAccount);
        return userAccountMapper.toDTO(result);
    }

    public UserAccountGetDTO updateUserAccount(Long userId, UserAccountUpdateDTO dto) {
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
}
