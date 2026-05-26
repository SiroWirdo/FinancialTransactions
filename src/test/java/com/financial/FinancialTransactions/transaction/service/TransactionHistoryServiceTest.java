package com.financial.FinancialTransactions.transaction.service;

import com.financial.FinancialTransactions.entity.TransactionHistory;
import com.financial.FinancialTransactions.general.enumaration.TransactionStatus;
import com.financial.FinancialTransactions.transaction.TransactionHistoryRepository;
import com.financial.FinancialTransactions.transaction.dto.TransactionHistoryDTO;
import com.financial.FinancialTransactions.transaction.dto.TransactionHistoryMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionHistoryServiceTest {

    @Mock
    TransactionHistoryRepository transactionHistoryRepository;

    @Mock
    TransactionHistoryMapper transactionHistoryMapper;

    @InjectMocks
    TransactionHistoryService transactionHistoryService;

    @Test
    void saveFailed() {
        TransactionHistory transactionHistory = new TransactionHistory();

        transactionHistoryService.saveFailed(transactionHistory);

        verify(transactionHistoryRepository).save(transactionHistory);

        assertEquals(TransactionStatus.FAILED, transactionHistory.getType());
    }

    @Test
    void getAccountTransactionsHistory() {
        var bankAccountId = 1L;
        var transactionHistory = new TransactionHistory();
        var transactionHistoryGetDTO = new TransactionHistoryDTO();

        when(transactionHistoryRepository.findTransactionHistoryByFromAccountId(bankAccountId))
                .thenReturn(List.of(transactionHistory));
        when(transactionHistoryRepository.findTransactionHistoryByToAccountId(bankAccountId))
                .thenReturn(new ArrayList<>());
        when(transactionHistoryMapper.toTransactionHistoryDTO(transactionHistory))
                .thenReturn(transactionHistoryGetDTO);

        List<TransactionHistoryDTO> result = transactionHistoryService.getAccountTransactionsHistory(bankAccountId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(transactionHistoryGetDTO, result.getFirst());
    }
}