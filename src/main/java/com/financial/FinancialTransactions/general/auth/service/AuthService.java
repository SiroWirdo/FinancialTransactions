package com.financial.FinancialTransactions.general.auth.service;

import com.financial.FinancialTransactions.entity.UserAccount;
import com.financial.FinancialTransactions.general.auth.dto.AuthResponse;
import com.financial.FinancialTransactions.general.auth.dto.LoginRequest;
import com.financial.FinancialTransactions.general.auth.dto.RegisterRequest;
import com.financial.FinancialTransactions.general.enumaration.Role;
import com.financial.FinancialTransactions.general.security.JwtService;
import com.financial.FinancialTransactions.user.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserAccountRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {

        UserAccount user = new UserAccount();
        user.setUserName(request.getUserName());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(Role.USER);

        userRepository.save(user);

        String jwt = jwtService.generateToken(
                new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPassword(),
                        List.of(
                                new SimpleGrantedAuthority(
                                        "ROLE_" + user.getRole().name()
                                )
                        )
                )
        );

        return new AuthResponse(jwt);
    }

    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        UserAccount user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        String jwt = jwtService.generateToken(
                new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPassword(),
                        List.of(
                                new SimpleGrantedAuthority(
                                        "ROLE_" + user.getRole().name()
                                )
                        )
                )
        );

        return new AuthResponse(jwt);
    }
}
