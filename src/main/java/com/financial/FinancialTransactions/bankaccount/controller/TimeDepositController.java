package com.financial.FinancialTransactions.bankaccount.controller;

import com.financial.FinancialTransactions.bankaccount.dto.DepositGetDTO;
import com.financial.FinancialTransactions.bankaccount.dto.DepositOpenNewDTO;
import com.financial.FinancialTransactions.bankaccount.service.TimeDepositService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/time-deposits")
public class TimeDepositController {

    private final TimeDepositService timeDepositService;

    public TimeDepositController(TimeDepositService timeDepositService) {
        this.timeDepositService = timeDepositService;
    }

    @GetMapping
    public List<DepositGetDTO> getTimeDepositsByBankAccountId(@RequestParam Long bankAccountId) {
        return timeDepositService.getAllDepositsForAccount(bankAccountId);
    }

    @PostMapping
    public ResponseEntity<Void> openNewTimeDeposit (@RequestBody DepositOpenNewDTO depositOpenNewDTO) {
        timeDepositService.openTimeDeposit(depositOpenNewDTO.getSourceBankAccountId(),
                depositOpenNewDTO.getAmount(),
                depositOpenNewDTO.getRate(),
                depositOpenNewDTO.getLengthInMonths());

        return ResponseEntity.ok().build();
    }
}
