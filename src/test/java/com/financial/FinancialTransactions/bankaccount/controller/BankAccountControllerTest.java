package com.financial.FinancialTransactions.bankaccount.controller;

import com.financial.FinancialTransactions.bankaccount.dto.BankAccountGetDTO;
import com.financial.FinancialTransactions.bankaccount.dto.BankAccountWithTransactionsDTO;
import com.financial.FinancialTransactions.bankaccount.service.BankAccountService;
import com.financial.FinancialTransactions.general.enumaration.TransactionStatus;
import com.financial.FinancialTransactions.transaction.dto.TransactionHistoryGetDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest(BankAccountController.class)
@AutoConfigureRestTestClient
@AutoConfigureMockMvc(addFilters = false)
class BankAccountControllerTest {
    @Autowired
    private RestTestClient restTestClient;

    @MockitoBean
    private BankAccountService bankAccountService;

    @Test
    void getBankAccounts() {
        var bankAccountGetDTO = new BankAccountGetDTO();
        bankAccountGetDTO.setBankAccountId(1L);
        bankAccountGetDTO.setBankAccountNumber("123456789");
        bankAccountGetDTO.setBalance(BigDecimal.TWO);
        bankAccountGetDTO.setUserName("test");

        List<BankAccountGetDTO> bankaccountList = new ArrayList<>();
        bankaccountList.add(bankAccountGetDTO);

        when(bankAccountService.getAllBankAccounts()).thenReturn(bankaccountList);

        restTestClient.get()
                .uri("/api/bank-accounts")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$[0].bankAccountId").isEqualTo(1)
                .jsonPath("$[0].userName").isEqualTo("test")
                .jsonPath("$[0].balance").isEqualTo(BigDecimal.TWO)
                .jsonPath("$[0].bankAccountNumber").isEqualTo("123456789");
    }

    @Test
    void getBankAccountWithTransactions() {
        var localDateTime = LocalDateTime.now();
        var outgoingTransactionsDTO = new TransactionHistoryGetDTO();
        outgoingTransactionsDTO.setFromBankAccountId(1L);
        outgoingTransactionsDTO.setToBankAccountId(2L);
        outgoingTransactionsDTO.setType(TransactionStatus.COMPLETED);
        outgoingTransactionsDTO.setDescription("test");
        outgoingTransactionsDTO.setAmount(BigDecimal.ONE);
        outgoingTransactionsDTO.setCreatedAt(localDateTime);

        var incomingTransactionsDTO = new TransactionHistoryGetDTO();
        incomingTransactionsDTO.setFromBankAccountId(2L);
        incomingTransactionsDTO.setToBankAccountId(1L);
        incomingTransactionsDTO.setType(TransactionStatus.COMPLETED);
        incomingTransactionsDTO.setDescription("test");
        incomingTransactionsDTO.setAmount(BigDecimal.ONE);
        incomingTransactionsDTO.setCreatedAt(localDateTime);

        var bankAccountWithTransactionsDTO = new BankAccountWithTransactionsDTO();
        bankAccountWithTransactionsDTO.setBankAccountId(1L);
        bankAccountWithTransactionsDTO.setBankAccountNumber("123456789");
        bankAccountWithTransactionsDTO.setBalance(BigDecimal.TWO);
        bankAccountWithTransactionsDTO.setUserName("test");
        bankAccountWithTransactionsDTO.setIncomingTransactions(List.of(incomingTransactionsDTO));
        bankAccountWithTransactionsDTO.setOutgoingTransactions(List.of(outgoingTransactionsDTO));

        when(bankAccountService.getBankAccountWithTransactions(1L)).thenReturn(bankAccountWithTransactionsDTO);

        restTestClient.get()
                .uri("/api/bank-accounts/bank-account?bankAccountId=1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.bankAccountId").isEqualTo(1)
                .jsonPath("$.userName").isEqualTo("test")
                .jsonPath("$.balance").isEqualTo(BigDecimal.TWO)
                .jsonPath("$.bankAccountNumber").isEqualTo("123456789")
                .jsonPath("$.incomingTransactions.length()").isEqualTo(1)
                .jsonPath("$.incomingTransactions[0].fromBankAccountId").isEqualTo(2)
                .jsonPath("$.incomingTransactions[0].toBankAccountId").isEqualTo(1)
                .jsonPath("$.incomingTransactions[0].type").isEqualTo(TransactionStatus.COMPLETED.toString())
                .jsonPath("$.incomingTransactions[0].description").isEqualTo("test")
                .jsonPath("$.incomingTransactions[0].amount").isEqualTo(BigDecimal.ONE)
                .jsonPath("$.incomingTransactions[0].createdAt")
                .value(v -> assertThat(LocalDateTime.parse(v.toString())).isEqualTo(localDateTime))
                .jsonPath("$.outgoingTransactions.length()").isEqualTo(1)
                .jsonPath("$.outgoingTransactions[0].fromBankAccountId").isEqualTo(1)
                .jsonPath("$.outgoingTransactions[0].toBankAccountId").isEqualTo(2)
                .jsonPath("$.outgoingTransactions[0].type").isEqualTo(TransactionStatus.COMPLETED.toString())
                .jsonPath("$.outgoingTransactions[0].description").isEqualTo("test")
                .jsonPath("$.outgoingTransactions[0].amount").isEqualTo(BigDecimal.ONE)
                .jsonPath("$.outgoingTransactions[0].createdAt")
                .value(v -> assertThat(LocalDateTime.parse(v.toString())).isEqualTo(localDateTime));
    }
}