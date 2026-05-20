package com.financial.FinancialTransactions.general.auth.controller;

import com.financial.FinancialTransactions.general.auth.dto.AuthResponse;
import com.financial.FinancialTransactions.general.auth.dto.LoginRequest;
import com.financial.FinancialTransactions.general.auth.dto.RegisterRequest;
import com.financial.FinancialTransactions.general.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(
            @RequestBody RegisterRequest request
    ) {

        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(
            @RequestBody LoginRequest request
    ) {

        return authService.login(request);
    }
}
