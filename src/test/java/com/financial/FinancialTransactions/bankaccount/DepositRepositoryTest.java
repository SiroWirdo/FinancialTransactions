package com.financial.FinancialTransactions.bankaccount;

import com.financial.FinancialTransactions.entity.BankAccount;
import com.financial.FinancialTransactions.entity.Deposit;
import com.financial.FinancialTransactions.entity.UserAccount;
import com.financial.FinancialTransactions.general.enumaration.DepositStatus;
import com.financial.FinancialTransactions.general.enumaration.DepositType;
import com.financial.FinancialTransactions.general.enumaration.Role;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DepositRepositoryTest {
    @Autowired
    DepositRepository depositRepository;

    @Autowired
    EntityManager entityManager;

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15");

    @Test
    void findAllByStatusAndMaturityDateLessThanEqual() {
        UserAccount userAccount = new UserAccount();
        userAccount.setUserName("UserName");
        userAccount.setFirstName("FirstName");
        userAccount.setLastName("LastName");
        userAccount.setPassword("password");
        userAccount.setEmail("email");
        userAccount.setRole(Role.USER);
        entityManager.persist(userAccount);

        BankAccount bankAccount = new BankAccount();
        bankAccount.setBalance(BigDecimal.valueOf(2000));
        bankAccount.setUserAccount(userAccount);
        bankAccount.setBankAccountNumber("123445");

        entityManager.persist(bankAccount);

        Deposit deposit = new Deposit();
        deposit.setMaturityDate(LocalDateTime.now().minusDays(2));
        deposit.setStatus(DepositStatus.ACTIVE);
        deposit.setSourceBankAccount(bankAccount);
        deposit.setAmount(BigDecimal.valueOf(2000));
        deposit.setLengthInMonths(2);
        deposit.setType(DepositType.TIMED);
        deposit.setRate(BigDecimal.valueOf(0.01));

        Deposit depositMaturityDateOutRange =  new Deposit();
        depositMaturityDateOutRange.setMaturityDate(LocalDateTime.now().plusMonths(1));
        depositMaturityDateOutRange.setStatus(DepositStatus.ACTIVE);
        depositMaturityDateOutRange.setSourceBankAccount(bankAccount);
        depositMaturityDateOutRange.setAmount(BigDecimal.valueOf(2000));
        depositMaturityDateOutRange.setLengthInMonths(2);
        depositMaturityDateOutRange.setType(DepositType.TIMED);
        depositMaturityDateOutRange.setRate(BigDecimal.valueOf(0.01));

        Deposit depositWrongStatus = new Deposit();
        depositWrongStatus.setMaturityDate(LocalDateTime.now().minusDays(2));
        depositWrongStatus.setStatus(DepositStatus.CLOSED);
        depositWrongStatus.setSourceBankAccount(bankAccount);
        depositWrongStatus.setAmount(BigDecimal.valueOf(2000));
        depositWrongStatus.setLengthInMonths(2);
        depositWrongStatus.setType(DepositType.TIMED);
        depositWrongStatus.setRate(BigDecimal.valueOf(0.01));

        entityManager.persist(deposit);
        entityManager.persist(depositMaturityDateOutRange);
        entityManager.persist(depositWrongStatus);
        entityManager.flush();

        List<Deposit> result = depositRepository
                .findAllByStatusAndMaturityDateLessThanEqual(DepositStatus.ACTIVE, LocalDateTime.now());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(deposit, result.getFirst());
    }

    @Test
    void findAllBySourceBankAccountId() {
        UserAccount userAccount = new UserAccount();
        userAccount.setUserName("UserName");
        userAccount.setFirstName("FirstName");
        userAccount.setLastName("LastName");
        userAccount.setPassword("password");
        userAccount.setEmail("email");
        userAccount.setRole(Role.USER);
        entityManager.persist(userAccount);

        BankAccount bankAccount = new BankAccount();
        bankAccount.setBalance(BigDecimal.valueOf(2000));
        bankAccount.setUserAccount(userAccount);
        bankAccount.setBankAccountNumber("123445");

        entityManager.persist(bankAccount);

        Deposit deposit = new Deposit();
        deposit.setMaturityDate(LocalDateTime.now().minusDays(2));
        deposit.setStatus(DepositStatus.ACTIVE);
        deposit.setSourceBankAccount(bankAccount);
        deposit.setAmount(BigDecimal.valueOf(2000));
        deposit.setLengthInMonths(2);
        deposit.setType(DepositType.TIMED);
        deposit.setRate(BigDecimal.valueOf(0.01));

        entityManager.persist(deposit);
        entityManager.flush();

        List<Deposit> result = depositRepository.findAllBySourceBankAccountId(bankAccount.getId());
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(deposit, result.getFirst());
    }
}