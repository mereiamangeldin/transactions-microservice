package com.github.mereiamangeldin.transactions_microservice.repository;

import com.github.mereiamangeldin.transactions_microservice.enums.ExpenseCategory;
import com.github.mereiamangeldin.transactions_microservice.model.Limit;
import com.github.mereiamangeldin.transactions_microservice.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT SUM(t.usdSum) FROM Transaction t WHERE t.monthYear = :monthYear AND t.expenseCategory = :expenseCategory")
    Float getTotalExpenseByCategoryOfCurrentMonth(
            @Param("monthYear") String monthYear,
            @Param("expenseCategory") ExpenseCategory expenseCategory
    );

    List<Transaction> findByMonthYear(String monthYear);

}
