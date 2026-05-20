package com.financial.FinancialTransactions.bankaccount.controller;

import com.financial.FinancialTransactions.bankaccount.dto.DepositGetDTO;
import com.financial.FinancialTransactions.bankaccount.dto.DepositOpenNewDTO;
import com.financial.FinancialTransactions.bankaccount.service.TimeDepositService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/time-deposits")
public class TimeDepositController {

    private final TimeDepositService timeDepositService;

    public TimeDepositController(TimeDepositService timeDepositService) {
        this.timeDepositService = timeDepositService;
    }

    @Operation(summary = "Retrieve all time deposits for a given bank account")
    @GetMapping
    public List<DepositGetDTO> getTimeDepositsByBankAccountId(@RequestParam Long bankAccountId) {
        return timeDepositService.getAllDepositsForAccount(bankAccountId);
    }

    @Operation(summary = "Open new time deposit")
    @PostMapping
    public ResponseEntity<Void> openNewTimeDeposit (@RequestBody DepositOpenNewDTO depositOpenNewDTO) {
        timeDepositService.openTimeDeposit(depositOpenNewDTO.getSourceBankAccountId(),
                depositOpenNewDTO.getAmount(),
                depositOpenNewDTO.getRate(),
                depositOpenNewDTO.getLengthInMonths());

        return ResponseEntity.ok().build();
    }
}
