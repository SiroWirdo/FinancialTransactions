package com.financial.FinancialTransactions.user.service;

import com.financial.FinancialTransactions.entity.UserAccount;
import com.financial.FinancialTransactions.exception.NotFoundException;
import com.financial.FinancialTransactions.user.UserAccountRepository;
import com.financial.FinancialTransactions.user.dto.UserAccountDTO;
import com.financial.FinancialTransactions.user.dto.UserAccountMapper;
import com.financial.FinancialTransactions.user.dto.UserAccountUpdateDTO;
import com.financial.FinancialTransactions.user.sevice.UserAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserAccountServiceTest {

    @Autowired
    UserAccountService userAccountService;

    @MockitoBean
    UserAccountRepository userAccountRepository;
    @MockitoBean
    UserAccountMapper userAccountMapper;

    @Test
    void getAllUserAccounts() {
        UserAccount userAccount = new UserAccount("uname", "fname", "lname", "pass");
        UserAccountDTO userAccountDTO = new UserAccountDTO("uname", "fname", "lname", "pass");
        List<UserAccount> userAccountList = new ArrayList<>();
        userAccountList.add(userAccount);

        when(userAccountRepository.findAll()).thenReturn(userAccountList);
        when(userAccountMapper.toDTO(userAccount)).thenReturn(userAccountDTO);

        List<UserAccountDTO> testUserList = userAccountService.getAllUserAccounts();
        assertNotNull(testUserList);
        assertEquals(userAccountList.size(), testUserList.size());

        UserAccountDTO testUser = testUserList.get(0);
        assertEquals(testUser, userAccountDTO);
    }

    @Test
    void createUserAccount() {
        UserAccountDTO testUser = new UserAccountDTO("uname", "fname", "lname", "pass");
        UserAccount expectedUser = new UserAccount("uname", "fname", "lname", "pass");

        when(userAccountMapper.toUserAccount(testUser)).thenReturn(expectedUser);

        userAccountService.createUserAccount(testUser);
        verify(userAccountRepository).save(expectedUser);
    }

    @Test
    void updateUserAccount() {
        Long userId = 1L;
        UserAccount userAccount = new UserAccount("uname", "fname", "lname", "pass");
        UserAccountUpdateDTO updateDTO = new UserAccountUpdateDTO();
        updateDTO.setFirstName("updatedFirstName");
        updateDTO.setLastName("updatedLastName");
        updateDTO.setPassword("updatedPassword");

        UserAccountDTO mappedDto = new UserAccountDTO("uname", "fname", "lname", "pass");

        when(userAccountRepository.findById(userId)).thenReturn(Optional.of(userAccount));
        when(userAccountMapper.toDTO(userAccount)).thenReturn(mappedDto);

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
        UserAccount userAccount = new UserAccount("uname", "fname", "lname", "pass");
        UserAccountUpdateDTO updateDTO = new UserAccountUpdateDTO();
        updateDTO.setFirstName("updatedFirstName");

        UserAccountDTO mappedDto = new UserAccountDTO("uname", "fname", "lname", "pass");

        when(userAccountRepository.findById(userId)).thenReturn(Optional.of(userAccount));
        when(userAccountMapper.toDTO(userAccount)).thenReturn(mappedDto);

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
                () -> userAccountService.updateUserAccount(userId, new UserAccountUpdateDTO())
        );

        verify(userAccountRepository, never()).save(any());
    }
}
