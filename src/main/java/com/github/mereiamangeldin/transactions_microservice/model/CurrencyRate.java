package com.github.mereiamangeldin.transactions_microservice.model;

import com.github.mereiamangeldin.transactions_microservice.enums.CurrencyShortname;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "currency_rates", indexes = @Index(columnList = "datetime"))
public class CurrencyRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "CHAR(3)")
    @Enumerated(EnumType.STRING)
    private CurrencyShortname currencyFrom;

    @Column(nullable = false, columnDefinition = "CHAR(3)")
    @Enumerated(EnumType.STRING)
    private CurrencyShortname currencyTo;

    private LocalDateTime datetime;

    private float rate;
}
