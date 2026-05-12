package com.financial.FinancialTransactions.bankaccount.scheduler;

import com.financial.FinancialTransactions.bankaccount.service.TimeDepositService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DepositScheduler {

    private final TimeDepositService timeDepositService;

    public DepositScheduler(TimeDepositService timeDepositService) {
        this.timeDepositService = timeDepositService;
    }

    @Scheduled(cron = "0 0 1 * * *") /* Every day at 1am */
    //@Scheduled(fixedRate = 100000)
    public void processDeposits() {

        log.info("Starting deposit processing...");

        timeDepositService.closeMaturedDeposits();

        log.info("Deposit processing finished.");
    }
}
