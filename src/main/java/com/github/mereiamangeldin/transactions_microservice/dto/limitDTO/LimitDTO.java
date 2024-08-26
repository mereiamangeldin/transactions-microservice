package com.github.mereiamangeldin.transactions_microservice.dto.limitDTO;

import com.github.mereiamangeldin.transactions_microservice.enums.CurrencyShortname;
import com.github.mereiamangeldin.transactions_microservice.enums.ExpenseCategory;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class LimitDTO {
    private Long id;
    private float sum;
    private LocalDateTime datetime;
    private CurrencyShortname currencyShortname;
    private ExpenseCategory expenseCategory;
    private String monthYear;

    public LimitDTO(float sum, LocalDateTime datetime, CurrencyShortname currencyShortname, ExpenseCategory expenseCategory, String monthYear) {
        this.sum = sum;
        this.datetime = datetime;
        this.currencyShortname = currencyShortname;
        this.expenseCategory = expenseCategory;
        this.monthYear = monthYear;
    }
}
