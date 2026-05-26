package com.financial.FinancialTransactions.general.auth.service;

import com.financial.FinancialTransactions.entity.BankAccount;
import com.financial.FinancialTransactions.entity.UserAccount;
import com.financial.FinancialTransactions.general.auth.dto.AuthResponseDTO;
import com.financial.FinancialTransactions.general.auth.dto.LoginRequestDTO;
import com.financial.FinancialTransactions.general.auth.dto.RegisterRequestDTO;
import com.financial.FinancialTransactions.general.enumaration.Role;
import com.financial.FinancialTransactions.general.security.JwtService;
import com.financial.FinancialTransactions.sequence.service.IbanGeneratorService;
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
    private final IbanGeneratorService ibanGeneratorService;

    public AuthResponseDTO register(RegisterRequestDTO request) {

        UserAccount user = new UserAccount();
        user.setUserName(request.getUserName());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());

        if (user.getRole().equals(Role.USER)){
            String iban = ibanGeneratorService.generateIBAN();
            BankAccount bankAccount = new BankAccount(user, iban);
            user.addBankAccount(bankAccount);
        }

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

        return new AuthResponseDTO(jwt);
    }

    public AuthResponseDTO login(LoginRequestDTO request) {

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

        return new AuthResponseDTO(jwt);
    }
}
