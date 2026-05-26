package com.financial.FinancialTransactions.user.controller;

import com.financial.FinancialTransactions.user.dto.UserAccountDTO;
import com.financial.FinancialTransactions.user.dto.UserAccountWithBankAccountsDTO;
import com.financial.FinancialTransactions.user.dto.UserAccountWithoutUserNameDTO;
import com.financial.FinancialTransactions.user.sevice.UserAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/users")
public class UserAccountController {
    private final UserAccountService userAccountService;

    public UserAccountController(UserAccountService userAccountService){
        this.userAccountService = userAccountService;
    }

    @Operation(summary = "Retrieve all user accounts")
    @GetMapping
    public List<UserAccountDTO> getUserList(){
        return userAccountService.getAllUserAccounts();}

    @Operation(summary = "Update user account")
    @PatchMapping("/{userId}")
    public ResponseEntity<UserAccountDTO> partiallyUpdateEmployee(@RequestBody UserAccountWithoutUserNameDTO updatedDTO, @PathVariable Long userId){
        UserAccountDTO updated = userAccountService.updateUserAccount(userId, updatedDTO);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Retrieve given user account with its bank acounts ids")
    @GetMapping ("/user-account")
    public UserAccountWithBankAccountsDTO getUserAccountWithBankAccounts(@RequestParam Long userAccountId) {
        return userAccountService.getUserAccountWithBankAccounts(userAccountId);
    }
}