package com.financial.FinancialTransactions.user.controller;

import com.financial.FinancialTransactions.user.dto.UserAccountGetDTO;
import com.financial.FinancialTransactions.user.dto.UserAccountUpdateDTO;
import com.financial.FinancialTransactions.user.sevice.UserAccountService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserAccountController {
    private final UserAccountService userAccountService;

    public UserAccountController(UserAccountService userAccountService){
        this.userAccountService = userAccountService;
    }

    @Operation(summary = "Retrieve all user accounts")
    @GetMapping
    public List<UserAccountGetDTO> getUserList(){
        return userAccountService.getAllUserAccounts();}

    @Operation(summary = "Create a new user account")
    @PostMapping
    public ResponseEntity<UserAccountGetDTO> createNewUser(@RequestBody UserAccountGetDTO usr){
        UserAccountGetDTO result = userAccountService.createUserAccount(usr);

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Update user account")
    @PatchMapping("/{userId}")
    public ResponseEntity<UserAccountGetDTO> partiallyUpdateEmployee(@RequestBody UserAccountUpdateDTO updatedDTO, @PathVariable Long userId){
        UserAccountGetDTO updated = userAccountService.updateUserAccount(userId, updatedDTO);
        return ResponseEntity.ok(updated);
    }
}