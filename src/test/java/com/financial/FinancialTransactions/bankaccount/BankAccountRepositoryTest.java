package com.financial.FinancialTransactions.bankaccount;

import com.financial.FinancialTransactions.entity.BankAccount;
import com.financial.FinancialTransactions.entity.UserAccount;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BankAccountRepositoryTest {
    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    EntityManager entityManager;

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15");

    @Test
    void findByIdForUpdate() {
        UserAccount userAccount = new UserAccount();
        entityManager.persist(userAccount);

        BankAccount bankAccount = new BankAccount();
        bankAccount.setBalance(BigDecimal.valueOf(2000));
        bankAccount.setUserAccount(userAccount);
        bankAccount.setBankAccountNumber("123445");

        entityManager.persist(bankAccount);
        entityManager.flush();

        Optional<BankAccount> result = bankAccountRepository.findByIdForUpdate(bankAccount.getId());

        assertTrue(result.isPresent());
        assertEquals(bankAccount.getBalance(), result.get().getBalance());
    }
}