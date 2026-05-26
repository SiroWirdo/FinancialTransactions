package com.financial.FinancialTransactions.bankaccount.controller;

import com.financial.FinancialTransactions.bankaccount.dto.DepositDTO;
import com.financial.FinancialTransactions.bankaccount.dto.DepositOpenNewDTO;
import com.financial.FinancialTransactions.bankaccount.service.TimeDepositService;
import com.financial.FinancialTransactions.general.enumaration.DepositStatus;
import com.financial.FinancialTransactions.general.enumaration.DepositType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureRestTestClient
@AutoConfigureMockMvc(addFilters = false)
class TimeDepositControllerTest {
    @Autowired
    RestTestClient restTestClient;

    @MockitoBean
    TimeDepositService timeDepositService;

    @Test
    void getTimeDepositsByBankAccountId() {
        LocalDateTime localDateTime = LocalDateTime.now();

        DepositDTO depositDTO = new DepositDTO();
        depositDTO.setId(1L);
        depositDTO.setSourceBankAccountId(1L);
        depositDTO.setDepositType(DepositType.TIMED);
        depositDTO.setRate(BigDecimal.ONE);
        depositDTO.setAmount(BigDecimal.ONE);
        depositDTO.setLengthInMonths(2);
        depositDTO.setMaturityDate(localDateTime);
        depositDTO.setStatus(DepositStatus.ACTIVE);

        List<DepositDTO> depositList = List.of(depositDTO);
        when(timeDepositService.getAllDepositsForAccount(1L)).thenReturn(depositList);

        restTestClient.get()
                .uri("/api/time-deposits?bankAccountId=1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$[0].id").isEqualTo(1L)
                .jsonPath("$[0].sourceBankAccountId").isEqualTo(1L)
                .jsonPath("$[0].depositType").isEqualTo(DepositType.TIMED.toString())
                .jsonPath("$[0].rate").isEqualTo(BigDecimal.ONE)
                .jsonPath("$[0].amount").isEqualTo(BigDecimal.ONE)
                .jsonPath("$[0].lengthInMonths").isEqualTo(2)
                .jsonPath("$[0].maturityDate")
                .value(v -> assertThat(LocalDateTime.parse(v.toString())).isEqualTo(localDateTime))
                .jsonPath("$[0].status").isEqualTo(DepositStatus.ACTIVE.toString());
    }

    @Test
    void openNewTimeDeposit() {
        DepositOpenNewDTO depositOpenNewDTO = new DepositOpenNewDTO();
        depositOpenNewDTO.setSourceBankAccountId(1L);
        depositOpenNewDTO.setRate(BigDecimal.ONE);
        depositOpenNewDTO.setAmount(BigDecimal.ONE);
        depositOpenNewDTO.setLengthInMonths(2);

        restTestClient.post()
                .uri("/api/time-deposits")
                .body(depositOpenNewDTO)
                .exchange()
                .expectStatus().isOk();
    }
}