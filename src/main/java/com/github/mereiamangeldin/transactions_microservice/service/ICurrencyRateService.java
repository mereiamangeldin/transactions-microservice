package com.github.mereiamangeldin.transactions_microservice.service;

import com.github.mereiamangeldin.transactions_microservice.enums.CurrencyShortname;

public interface ICurrencyRateService {
    float getCurrentRateByCurrencies(CurrencyShortname currencyFrom, CurrencyShortname currencyTo);
}
