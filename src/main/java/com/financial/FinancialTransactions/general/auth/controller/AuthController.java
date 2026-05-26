package com.financial.FinancialTransactions.general.auth.controller;

import com.financial.FinancialTransactions.general.auth.dto.AuthResponseDTO;
import com.financial.FinancialTransactions.general.auth.dto.LoginRequestDTO;
import com.financial.FinancialTransactions.general.auth.dto.RegisterRequestDTO;
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
    public AuthResponseDTO register(
            @RequestBody RegisterRequestDTO request
    ) {

        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponseDTO login(
            @RequestBody LoginRequestDTO request
    ) {

        return authService.login(request);
    }
}
