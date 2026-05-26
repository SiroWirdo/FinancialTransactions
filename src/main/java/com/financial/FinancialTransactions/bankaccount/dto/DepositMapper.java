package com.financial.FinancialTransactions.bankaccount.dto;

import com.financial.FinancialTransactions.entity.Deposit;
import org.springframework.stereotype.Component;

@Component
public class DepositMapper {
    public DepositDTO toDepositGetDTO(Deposit deposit) {
        DepositDTO depositDTO = new DepositDTO();
        depositDTO.setId(deposit.getId());
        depositDTO.setSourceBankAccountId(deposit.getSourceBankAccount().getId());
        depositDTO.setDepositType(deposit.getType());
        depositDTO.setRate(deposit.getRate());
        depositDTO.setAmount(deposit.getAmount());
        depositDTO.setLengthInMonths(deposit.getLengthInMonths());
        depositDTO.setMaturityDate(deposit.getMaturityDate());
        depositDTO.setStatus(deposit.getStatus());

        return depositDTO;
    }
}
