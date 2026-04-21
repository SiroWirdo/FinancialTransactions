package com.financial.FinancialTransactions.transaction;

import com.financial.FinancialTransactions.entity.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {

    @Query("SELECT t FROM TransactionHistory t WHERE t.fromBankAccount.id = :id")
    List<TransactionHistory> findTransactionHistoryByFromAccountId(@Param("id") Long fromAccountId);

    @Query("SELECT t FROM TransactionHistory t WHERE t.toBankAccount.id = :id")
    List<TransactionHistory> findTransactionHistoryByToAccountId(@Param("id") Long toAccountId);

}
