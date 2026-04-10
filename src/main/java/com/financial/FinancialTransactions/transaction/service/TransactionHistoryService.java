package com.financial.FinancialTransactions.transaction.service;

import com.financial.FinancialTransactions.entity.TransactionHistory;
import com.financial.FinancialTransactions.general.enumaration.TransactionStatus;
import com.financial.FinancialTransactions.transaction.TransactionHistoryRepository;
import com.financial.FinancialTransactions.transaction.dto.TransactionHistoryGetDTO;
import com.financial.FinancialTransactions.transaction.dto.TransactionHistoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class TransactionHistoryService {
    TransactionHistoryRepository transactionHistoryRepository;
    TransactionHistoryMapper transactionHistoryMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveFailed(TransactionHistory transactionHistory) {
        transactionHistory.setType(TransactionStatus.FAILED);
        transactionHistoryRepository.save(transactionHistory);
    }

    public List<TransactionHistoryGetDTO> getAccountTransactionsHistory(Long bankAccountId) {
        List<TransactionHistory> transactionFrom = transactionHistoryRepository.findTransactionHistoryByFromAccountId(bankAccountId)
                .orElse(new ArrayList<>());
        List<TransactionHistory> transactionTo = transactionHistoryRepository.findTransactionHistoryByToAccountId(bankAccountId)
                .orElse(new ArrayList<>());

        ArrayList<TransactionHistoryGetDTO> result = new ArrayList<>();

        transactionFrom.forEach(transaction -> {
            result.add(transactionHistoryMapper.toTransactionHistoryDTO(transaction));
        });
        transactionTo.forEach(transaction -> {
            result.add(transactionHistoryMapper.toTransactionHistoryDTO(transaction));
        });

        return result;
    }
}
