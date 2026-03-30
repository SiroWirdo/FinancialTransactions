package com.financial.FinancialTransactions.sequence.service;

import com.financial.FinancialTransactions.general.Constants;
import com.financial.FinancialTransactions.sequence.SequenceRepository;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class IbanGeneratorService {
    private final SequenceRepository sequenceRepository;

    public IbanGeneratorService(SequenceRepository sequenceRepository) {
        this.sequenceRepository = sequenceRepository;
    }

    public String generateIBAN() {
        Long seq = sequenceRepository.getIbanNextValue();

        String accountNumber = String.format("%016d", seq);
        String temp = Constants.BANK_ID + accountNumber + Constants.COUNTRY_NBR + "00";

        BigInteger number = new BigInteger(temp);
        int checkDigits = 98 - number.mod(BigInteger.valueOf(97)).intValue();

        return Constants.PL_COUNTRY_CODE + String.format("%02d", checkDigits) + Constants.BANK_ID + accountNumber;
    }
}
