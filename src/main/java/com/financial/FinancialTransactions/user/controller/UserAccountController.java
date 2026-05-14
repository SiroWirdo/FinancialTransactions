package com.financial.FinancialTransactions.user.controller;

import com.financial.FinancialTransactions.user.dto.UserAccountGetDTO;
import com.financial.FinancialTransactions.user.dto.UserAccountUpdateDTO;
import com.financial.FinancialTransactions.user.sevice.UserAccountService;
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

    @GetMapping
    public List<UserAccountGetDTO> getUserList(){
        return userAccountService.getAllUserAccounts();}

    @PostMapping
    public ResponseEntity<UserAccountGetDTO> createNewUser(@RequestBody UserAccountGetDTO usr){
        UserAccountGetDTO result = userAccountService.createUserAccount(usr);

        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserAccountGetDTO> partiallyUpdateEmployee(@RequestBody UserAccountUpdateDTO updatedDTO, @PathVariable Long userId){
        UserAccountGetDTO updated = userAccountService.updateUserAccount(userId, updatedDTO);
        return ResponseEntity.ok(updated);
    }
}