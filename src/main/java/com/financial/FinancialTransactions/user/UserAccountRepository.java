package com.financial.FinancialTransactions.user;

import com.financial.FinancialTransactions.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    UserAccount findByUserName(String userName);
}

