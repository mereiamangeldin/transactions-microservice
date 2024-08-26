package com.github.mereiamangeldin.transactions_microservice.model;

import com.github.mereiamangeldin.transactions_microservice.enums.CurrencyShortname;
import com.github.mereiamangeldin.transactions_microservice.enums.ExpenseCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "limits", indexes = @Index(columnList = "monthYear, expenseCategory"))
public class Limit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(scale = 2)
    private float sum;

    private LocalDateTime datetime;

    @Column(nullable = false, columnDefinition = "CHAR(3)")
    @Enumerated(EnumType.STRING)
    private CurrencyShortname currencyShortname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ExpenseCategory expenseCategory;

    @Column(nullable = false, columnDefinition = "CHAR(7)")
    private String monthYear;
}
