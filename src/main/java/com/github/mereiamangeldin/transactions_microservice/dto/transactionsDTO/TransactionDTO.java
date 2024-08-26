package com.github.mereiamangeldin.transactions_microservice.dto.transactionsDTO;

import com.github.mereiamangeldin.transactions_microservice.enums.CurrencyShortname;
import com.github.mereiamangeldin.transactions_microservice.enums.ExpenseCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private Long id;
    private String accountFrom;
    private String accountTo;
    private CurrencyShortname currencyShortname;
    private float amount;
    private ExpenseCategory expenseCategory;
    private LocalDateTime datetime;
    private boolean limitExceeded;
    private String monthYear;
    private float usdSum;
}
