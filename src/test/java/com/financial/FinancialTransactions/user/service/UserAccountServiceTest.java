package com.financial.FinancialTransactions.user.service;

import com.financial.FinancialTransactions.entity.UserAccount;
import com.financial.FinancialTransactions.exception.NotFoundException;
import com.financial.FinancialTransactions.general.enumaration.Role;
import com.financial.FinancialTransactions.sequence.service.IbanGeneratorService;
import com.financial.FinancialTransactions.user.UserAccountRepository;
import com.financial.FinancialTransactions.user.dto.UserAccountDTO;
import com.financial.FinancialTransactions.user.dto.UserAccountMapper;
import com.financial.FinancialTransactions.user.dto.UserAccountWithoutUserNameDTO;
import com.financial.FinancialTransactions.user.sevice.UserAccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserAccountServiceTest {
    @Mock
    UserAccountRepository userAccountRepository;
    @Mock
    UserAccountMapper userAccountMapper;
    @Mock
    IbanGeneratorService ibanGeneratorService;
    @InjectMocks
    UserAccountService userAccountService;

    @Test
    void getAllUserAccounts() {
        UserAccount userAccount = new UserAccount("uname", "fname", "lname", "pass", Role.USER, "email");
        UserAccountDTO userAccountDTO = new UserAccountDTO();
        userAccountDTO.setUserName("uname");
        userAccountDTO.setFirstName("fname");
        userAccountDTO.setLastName("lname");
        userAccountDTO.setPassword("pass");

        List<UserAccount> userAccountList = new ArrayList<>();
        userAccountList.add(userAccount);

        when(userAccountRepository.findAll()).thenReturn(userAccountList);
        when(userAccountMapper.toDTO(userAccount)).thenReturn(userAccountDTO);

        List<UserAccountDTO> testUserList = userAccountService.getAllUserAccounts();
        assertNotNull(testUserList);
        assertEquals(userAccountList.size(), testUserList.size());

        UserAccountDTO testUser = testUserList.getFirst();
        assertEquals(testUser, userAccountDTO);
    }

    @Test
    void updateUserAccount() {
        Long userId = 1L;
        UserAccount userAccount = new UserAccount("uname", "fname", "lname", "pass", Role.USER, "email");
        UserAccountWithoutUserNameDTO updateDTO = new UserAccountWithoutUserNameDTO();
        updateDTO.setFirstName("updatedFirstName");
        updateDTO.setLastName("updatedLastName");
        updateDTO.setPassword("updatedPassword");

        UserAccountDTO mappedDto = new UserAccountDTO();
        mappedDto.setUserName("uname");
        mappedDto.setFirstName("fname");
        mappedDto.setLastName("lname");
        mappedDto.setPassword("pass");

        when(userAccountRepository.findById(userId)).thenReturn(Optional.of(userAccount));
        when(userAccountMapper.toDTO(any(UserAccount.class))).thenReturn(mappedDto);
        when(userAccountRepository.save(any(UserAccount.class))).thenReturn(userAccount);

        UserAccountDTO result = userAccountService.updateUserAccount(userId, updateDTO);
        assertEquals(result, mappedDto);
        assertEquals("updatedFirstName", userAccount.getFirstName());
        assertEquals("updatedLastName", userAccount.getLastName());
        assertEquals("updatedPassword", userAccount.getPassword());

        verify(userAccountRepository).save(userAccount);
        verify(userAccountMapper).toDTO(userAccount);
    }

    @Test
    void partialUpdateUserAccount() {
        Long userId = 1L;
        UserAccount userAccount = new UserAccount("uname", "fname", "lname", "pass", Role.USER, "email");
        UserAccountWithoutUserNameDTO updateDTO = new UserAccountWithoutUserNameDTO();
        updateDTO.setFirstName("updatedFirstName");

        UserAccountDTO mappedDto = new UserAccountDTO();
        mappedDto.setUserName("uname");
        mappedDto.setFirstName("fname");
        mappedDto.setLastName("lname");
        mappedDto.setPassword("pass");

        when(userAccountRepository.findById(userId)).thenReturn(Optional.of(userAccount));
        when(userAccountMapper.toDTO(any(UserAccount.class))).thenReturn(mappedDto);
        when(userAccountRepository.save(any(UserAccount.class))).thenReturn(userAccount);

        UserAccountDTO result = userAccountService.updateUserAccount(userId, updateDTO);
        assertEquals(result, mappedDto);
        assertEquals("updatedFirstName", userAccount.getFirstName());
        assertEquals("lname", userAccount.getLastName());
        assertEquals("pass", userAccount.getPassword());
        verify(userAccountRepository).save(userAccount);
        verify(userAccountMapper).toDTO(userAccount);
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        Long userId = 1L;

        when(userAccountRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> userAccountService.updateUserAccount(userId, new UserAccountWithoutUserNameDTO())
        );

        verify(userAccountRepository, never()).save(any());
    }
}
