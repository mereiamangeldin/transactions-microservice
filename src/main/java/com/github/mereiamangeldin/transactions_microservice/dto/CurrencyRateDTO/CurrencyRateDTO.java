package com.github.mereiamangeldin.transactions_microservice.dto.CurrencyRateDTO;

import com.github.mereiamangeldin.transactions_microservice.enums.CurrencyShortname;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
public class CurrencyRateDTO {
    private Long id;
    private CurrencyShortname currencyFrom;
    private CurrencyShortname currencyTo;
    private LocalDateTime datetime;
    private float rate;

    public CurrencyRateDTO(CurrencyShortname currencyFrom, CurrencyShortname currencyTo, float rate, LocalDateTime datetime) {
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.rate = rate;
        this.datetime = datetime;

    }
}
