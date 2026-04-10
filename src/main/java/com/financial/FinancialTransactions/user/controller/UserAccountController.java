package com.financial.FinancialTransactions.user.controller;

import com.financial.FinancialTransactions.user.dto.UserAccountDTO;
import com.financial.FinancialTransactions.user.dto.UserAccountUpdateDTO;
import com.financial.FinancialTransactions.user.sevice.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserAccountController {
    private final UserAccountService userAccountService;

    @Autowired
    public UserAccountController(UserAccountService userAccountService){
        this.userAccountService = userAccountService;
    }

    @GetMapping
    public List<UserAccountDTO> getUserList(){
        return userAccountService.getAllUserAccounts();}

    @PostMapping
    public ResponseEntity<UserAccountDTO> createNewUser(@RequestBody UserAccountDTO usr){
        UserAccountDTO result = userAccountService.createUserAccount(usr);

        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserAccountDTO> partiallyUpdateEmployee(@RequestBody UserAccountUpdateDTO updatedDTO, @PathVariable Long userId){
        UserAccountDTO updated = userAccountService.updateUserAccount(userId, updatedDTO);
        return ResponseEntity.ok(updated);
    }
}