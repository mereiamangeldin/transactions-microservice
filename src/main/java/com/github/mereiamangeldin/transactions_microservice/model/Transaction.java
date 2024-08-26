package com.github.mereiamangeldin.transactions_microservice.model;

import com.github.mereiamangeldin.transactions_microservice.enums.CurrencyShortname;
import com.github.mereiamangeldin.transactions_microservice.enums.ExpenseCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "transactions", indexes = @Index(name = "idx_month_year", columnList = "monthYear"))
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "CHAR(10)")
    private String accountFrom;

    @Column(nullable = false, columnDefinition = "CHAR(10)")
    private String accountTo;

    @Column(nullable = false, columnDefinition = "CHAR(3)")
    @Enumerated(EnumType.STRING)
    private CurrencyShortname currencyShortname;

    @Column(scale = 2)
    private float amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ExpenseCategory expenseCategory;

    private LocalDateTime datetime;

    @Column(nullable = false)
    private boolean limitExceeded;

    @Column(nullable = false, columnDefinition = "CHAR(7)")
    private String monthYear;

    @Column(nullable = false)
    private float usdSum;

}

