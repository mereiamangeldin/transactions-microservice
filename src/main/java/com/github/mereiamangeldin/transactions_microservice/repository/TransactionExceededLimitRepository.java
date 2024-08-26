package com.github.mereiamangeldin.transactions_microservice.repository;

import com.github.mereiamangeldin.transactions_microservice.model.TransactionExceededLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionExceededLimitRepository extends JpaRepository<TransactionExceededLimit, Long> {
}
