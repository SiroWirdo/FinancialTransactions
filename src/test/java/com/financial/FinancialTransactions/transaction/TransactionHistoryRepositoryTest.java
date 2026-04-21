package com.financial.FinancialTransactions.transaction;

import com.financial.FinancialTransactions.bankaccount.BankAccountRepository;
import com.financial.FinancialTransactions.entity.BankAccount;
import com.financial.FinancialTransactions.entity.TransactionHistory;
import com.financial.FinancialTransactions.entity.UserAccount;
import com.financial.FinancialTransactions.general.enumaration.TransactionStatus;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TransactionHistoryRepositoryTest {
    @Autowired
    TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void findTransactionHistoryByFromAccountId(){
        UserAccount userAccount = new UserAccount();
        entityManager.persist(userAccount);

        BankAccount fromAccount = new BankAccount();
        fromAccount.setUserAccount(userAccount);
        fromAccount.setBankAccountNumber("1234");
        entityManager.persist(fromAccount);

        BankAccount toAccount = new BankAccount();
        toAccount.setUserAccount(userAccount);
        toAccount.setBankAccountNumber("987653");
        entityManager.persist(toAccount);

        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setFromBankAccount(fromAccount);
        transactionHistory.setToBankAccount(toAccount);
        transactionHistory.setAmount(BigDecimal.valueOf(1000));
        transactionHistory.setType(TransactionStatus.COMPLETED);
        transactionHistory.setCreatedAt(LocalDateTime.now());
        entityManager.persist(transactionHistory);
        entityManager.flush();

        List<TransactionHistory> result = transactionHistoryRepository.findTransactionHistoryByFromAccountId(fromAccount.getId());

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(fromAccount.getId(),result.getFirst().getFromBankAccount().getId());
    }

    @Test
    void findTransactionHistoryByToAccountId(){
        UserAccount userAccount = new UserAccount();
        entityManager.persist(userAccount);

        BankAccount fromAccount = new BankAccount();
        fromAccount.setUserAccount(userAccount);
        fromAccount.setBankAccountNumber("1234");
        entityManager.persist(fromAccount);

        BankAccount toAccount = new BankAccount();
        toAccount.setUserAccount(userAccount);
        toAccount.setBankAccountNumber("987653");
        entityManager.persist(toAccount);

        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setFromBankAccount(fromAccount);
        transactionHistory.setToBankAccount(toAccount);
        transactionHistory.setAmount(BigDecimal.valueOf(1000));
        transactionHistory.setType(TransactionStatus.COMPLETED);
        transactionHistory.setCreatedAt(LocalDateTime.now());
        entityManager.persist(transactionHistory);
        entityManager.flush();

        List<TransactionHistory> result = transactionHistoryRepository.findTransactionHistoryByToAccountId(toAccount.getId());

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(toAccount.getId(),result.getFirst().getToBankAccount().getId());
    }
}