package com.financial.FinancialTransactions.bankaccount;

import com.financial.FinancialTransactions.entity.BankAccount;
import com.financial.FinancialTransactions.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
}
