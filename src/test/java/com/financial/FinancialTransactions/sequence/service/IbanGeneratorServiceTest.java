package com.financial.FinancialTransactions.sequence.service;

import com.financial.FinancialTransactions.sequence.SequenceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class IbanGeneratorServiceTest {

    @Autowired
    IbanGeneratorService ibanGeneratorService;

    @MockitoBean
    SequenceRepository sequenceRepository;

    @Test
    void generateIBAN() {
        when(sequenceRepository.getIbanNextValue()).thenReturn(123L);

        String iban = ibanGeneratorService.generateIBAN();

        assertEquals("PL07123498760000000000000123", iban);
    }
}