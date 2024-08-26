package com.github.mereiamangeldin.transactions_microservice.dto.transactionsDTO;

import com.github.mereiamangeldin.transactions_microservice.enums.CurrencyShortname;
import com.github.mereiamangeldin.transactions_microservice.enums.ExpenseCategory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionCreateDTO {
    @NotNull(message = "AccountFrom cannot be null")
    @Pattern(regexp = "\\d{10}", message = "AccountFrom must contain only 10 digits")
    private String accountFrom;

    @NotNull(message = "AccountTo cannot be null")
    @Pattern(regexp = "\\d{10}", message = "AccountTo must contain only 10 digits")
    private String accountTo;

    @NotNull(message = "Amount cannot be null")
    private float amount;

    @NotNull(message = "ExpenseCategory cannot be null")
    @Pattern(regexp = "SERVICE|GOOD", message = "Expense category must be one of: SERVICE|GOOD")
    private String expenseCategory;

    @NotNull(message = "CurrencyShortname cannot be null")
    @Pattern(regexp = "KZT|USD|RUB", message = "CurrencyShortname must be one of: KZT, USD, RUB")
    private String currencyShortname;
}
