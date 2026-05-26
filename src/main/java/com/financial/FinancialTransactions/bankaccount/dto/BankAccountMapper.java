package com.financial.FinancialTransactions.bankaccount.dto;

import com.financial.FinancialTransactions.entity.BankAccount;
import com.financial.FinancialTransactions.transaction.dto.TransactionHistoryMapper;
import org.springframework.stereotype.Component;

@Component
public class BankAccountMapper {

    private final TransactionHistoryMapper transactionHistoryMapper;

    public BankAccountMapper(TransactionHistoryMapper transactionHistoryMapper) {
        this.transactionHistoryMapper = transactionHistoryMapper;
    }

    public BankAccountDTO toBankAccountGetDTO(BankAccount bankAccount) {
        BankAccountDTO dto = new BankAccountDTO();
        dto.setBankAccountId(bankAccount.getId());
        dto.setUserName(bankAccount.getUserAccount().getUserName());
        dto.setBankAccountNumber(bankAccount.getBankAccountNumber());
        dto.setBalance(bankAccount.getBalance());

        return dto;
    }

    public BankAccountWithTransactionsDTO toBankAccountWithTransactionsDTO(BankAccount bankAccount) {
        BankAccountWithTransactionsDTO dto = new BankAccountWithTransactionsDTO();
        dto.setBankAccountId(bankAccount.getId());
        dto.setUserName(bankAccount.getUserAccount().getUserName());
        dto.setBankAccountNumber(bankAccount.getBankAccountNumber());
        dto.setBalance(bankAccount.getBalance());
        dto.setOutgoingTransactions(bankAccount.getOutgoingTransactions()
                .stream()
                .map(transactionHistoryMapper::toTransactionHistoryDTO)
                .toList());
        dto.setIncomingTransactions(bankAccount.getIncomingTransactions()
                .stream()
                .map(transactionHistoryMapper::toTransactionHistoryDTO)
                .toList());
        return dto;
    }
}
