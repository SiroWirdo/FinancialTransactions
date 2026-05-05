package com.financial.FinancialTransactions.transaction.controller;

import com.financial.FinancialTransactions.bankaccount.controller.BankAccountController;
import com.financial.FinancialTransactions.entity.TransactionHistory;
import com.financial.FinancialTransactions.transaction.dto.TransactionHistoryGetDTO;
import com.financial.FinancialTransactions.transaction.service.TransactionHistoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest(TransactionHistoryController.class)
@AutoConfigureRestTestClient
@AutoConfigureMockMvc(addFilters = false)
class TransactionHistoryControllerTest {
    @Autowired
    private RestTestClient restTestClient;

    @MockitoBean
    private TransactionHistoryService transactionHistoryService;

    @Test
    void getTransactionsHistory() {
        var fromBankAccountId = 1l;
        var toBankAccountId = 2l;
        var localDateTime = LocalDateTime.now();
        var transactionHistoryGetDTO = new TransactionHistoryGetDTO();
        transactionHistoryGetDTO.setFromBankAccountId(fromBankAccountId);
        transactionHistoryGetDTO.setToBankAccountId(toBankAccountId);
        transactionHistoryGetDTO.setAmount(BigDecimal.ONE);
        transactionHistoryGetDTO.setCreatedAt(localDateTime);
        transactionHistoryGetDTO.setDescription("description");

        when(transactionHistoryService.getAccountTransactionsHistory(fromBankAccountId))
                .thenReturn(List.of(transactionHistoryGetDTO));

        restTestClient.get()
                .uri("/api/transactions-history?bankAccountId=1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$.[0].fromBankAccountId").isEqualTo(fromBankAccountId)
                .jsonPath("$.[0].toBankAccountId").isEqualTo(toBankAccountId)
                .jsonPath("$.[0].description").isEqualTo("description")
                .jsonPath("$.[0].amount").isEqualTo(BigDecimal.ONE)
                .jsonPath("$.[0].createdAt")
                .value(v -> assertThat(LocalDateTime.parse(v.toString())).isEqualTo(localDateTime));
    }
}