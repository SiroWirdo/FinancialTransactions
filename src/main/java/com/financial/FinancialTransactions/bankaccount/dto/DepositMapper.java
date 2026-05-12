package com.financial.FinancialTransactions.bankaccount.dto;

import com.financial.FinancialTransactions.entity.Deposit;
import org.springframework.stereotype.Component;

@Component
public class DepositMapper {
    public DepositGetDTO toDepositGetDTO(Deposit deposit) {
        DepositGetDTO depositGetDTO = new DepositGetDTO();
        depositGetDTO.setId(deposit.getId());
        depositGetDTO.setSourceBankAccountId(deposit.getSourceBankAccount().getId());
        depositGetDTO.setDepositType(deposit.getType());
        depositGetDTO.setRate(deposit.getRate());
        depositGetDTO.setAmount(deposit.getAmount());
        depositGetDTO.setLengthInMonths(deposit.getLengthInMonths());
        depositGetDTO.setMaturityDate(deposit.getMaturityDate());
        depositGetDTO.setStatus(deposit.getStatus());

        return depositGetDTO;
    }
}
