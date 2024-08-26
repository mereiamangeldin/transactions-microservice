package com.github.mereiamangeldin.transactions_microservice.dto.limitDTO;

import com.github.mereiamangeldin.transactions_microservice.enums.CurrencyShortname;
import com.github.mereiamangeldin.transactions_microservice.enums.ExpenseCategory;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LimitCreateDTO {
    @NotNull(message = "Sum cannot be null")
    @DecimalMin(value = "0.01", inclusive = true, message = "Sum must be greater than zero")
    private Float sum;

    @NotNull(message = "Currency shortname cannot be null")
    @Pattern(regexp = "KZT|USD|RUB", message = "Currency shortname must be one of: KZT, USD, RUB")
    private String currencyShortname;

    @NotNull(message = "Expense category cannot be null")
    @Pattern(regexp = "SERVICE|GOOD", message = "Expense category must be one of: SERVICE|GOOD")
    private String expenseCategory;
}
