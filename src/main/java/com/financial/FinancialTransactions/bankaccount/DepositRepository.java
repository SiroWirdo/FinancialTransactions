package com.financial.FinancialTransactions.bankaccount;

import com.financial.FinancialTransactions.entity.Deposit;
import com.financial.FinancialTransactions.general.enumaration.DepositStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, Long> {
    @Query("SELECT d FROM Deposit d WHERE d.status = :status AND d.maturityDate <= :maturityDate")
    List<Deposit> findAllByStatusAndMaturityDateLessThanEqual(@Param("status") DepositStatus status,
                                                              @Param("maturityDate") LocalDateTime maturityDate);

    @Query("SELECT d FROM Deposit d WHERE d.sourceBankAccount.id = :sourceBankAccountId")
    List<Deposit> findAllBySourceBankAccountId(@Param("sourceBankAccountId") Long sourceBankAccountId);
}
