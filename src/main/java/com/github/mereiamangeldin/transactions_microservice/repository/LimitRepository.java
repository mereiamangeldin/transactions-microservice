package com.github.mereiamangeldin.transactions_microservice.repository;

import com.github.mereiamangeldin.transactions_microservice.enums.ExpenseCategory;
import com.github.mereiamangeldin.transactions_microservice.model.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LimitRepository extends JpaRepository<Limit, Long> {
    @Query("SELECT l FROM Limit l WHERE l.monthYear = :monthYear AND l.expenseCategory = :expenseCategory ORDER BY l.datetime DESC limit 1")
    Limit getCurrentLimitByExpenseCategory(
            @Param("monthYear") String monthYear,
            @Param("expenseCategory") ExpenseCategory expenseCategory
    );

    List<Limit> findByMonthYear(String monthYear);
}
