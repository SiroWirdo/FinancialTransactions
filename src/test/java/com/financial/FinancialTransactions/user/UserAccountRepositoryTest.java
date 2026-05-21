package com.financial.FinancialTransactions.user;

import com.financial.FinancialTransactions.entity.UserAccount;
import com.financial.FinancialTransactions.general.enumaration.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserAccountRepositoryTest {

    @Autowired
    UserAccountRepository userAccountRepository;

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15");

    @Test
    public void testFindByUserName(){
        UserAccount userAccount = new UserAccount("UserName", "FirstName", "LastName", "password", Role.USER, "email");
        userAccountRepository.save(userAccount);

        assertTrue(userAccountRepository.findByUserName(userAccount.getUserName()).isPresent());
    }
}
