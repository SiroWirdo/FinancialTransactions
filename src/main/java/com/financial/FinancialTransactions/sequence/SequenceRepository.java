package com.financial.FinancialTransactions.sequence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class SequenceRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Long getIbanNextValue() {
        return ((Number) entityManager
                .createNativeQuery("SELECT nextval('iban_seq')")
                .getSingleResult())
                .longValue();
    }
}
