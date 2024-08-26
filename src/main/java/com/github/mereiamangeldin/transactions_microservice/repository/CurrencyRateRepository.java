package com.github.mereiamangeldin.transactions_microservice.repository;

import com.github.mereiamangeldin.transactions_microservice.enums.CurrencyShortname;
import com.github.mereiamangeldin.transactions_microservice.model.CurrencyRate;
import com.github.mereiamangeldin.transactions_microservice.model.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {
    @Query("SELECT cr.rate FROM CurrencyRate cr WHERE cr.currencyFrom = :currencyFrom AND cr.currencyTo = :currencyTo ORDER BY cr.datetime DESC limit 1")
    Float getCurrentRateByCurrencies(
            @Param("currencyFrom") CurrencyShortname currencyFrom,
            @Param("currencyTo") CurrencyShortname currencyTo
    );
}
