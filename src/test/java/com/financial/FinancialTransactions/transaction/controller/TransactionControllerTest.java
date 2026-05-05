package com.financial.FinancialTransactions.transaction.controller;

import com.financial.FinancialTransactions.bankaccount.controller.BankAccountController;
import com.financial.FinancialTransactions.transaction.dto.TransactionDepositDTO;
import com.financial.FinancialTransactions.transaction.dto.TransactionTransferDTO;
import com.financial.FinancialTransactions.transaction.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest(TransactionController.class)
@AutoConfigureRestTestClient
@AutoConfigureMockMvc(addFilters = false)
class TransactionControllerTest {

    @Autowired
    private RestTestClient restTestClient;

    @MockitoBean
    private TransactionService transactionService;

    @Test
    void transfer() {
        var request = new TransactionTransferDTO();
        request.setFromBankAccountId(1L);
        request.setToBankAccountId(2L);
        request.setAmount(BigDecimal.ONE);
        request.setDescription("test");

        restTestClient.post()
                .uri("/api/transactions/transfer")
                .body(request)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void deposit() {
        var request = new TransactionDepositDTO();
        request.setBankAccountId(1L);
        request.setAmount(BigDecimal.ONE);

        restTestClient.post()
                .uri("/api/transactions/deposit")
                .body(request)
                .exchange()
                .expectStatus().isOk();
    }
}