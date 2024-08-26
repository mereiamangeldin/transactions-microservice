package com.github.mereiamangeldin.transactions_microservice.service;

import com.github.mereiamangeldin.transactions_microservice.dto.CurrencyRateDTO.CurrencyRateDTO;
import com.github.mereiamangeldin.transactions_microservice.enums.CurrencyShortname;
import com.github.mereiamangeldin.transactions_microservice.model.CurrencyRate;
import com.github.mereiamangeldin.transactions_microservice.repository.CurrencyRateRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CurrencyRateService implements ICurrencyRateService {
    private static final Logger logger = LoggerFactory.getLogger(CurrencyRateService.class);

    private final CurrencyRateRepository currencyRateRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private final String url = "https://www.alphavantage.co/query?function=FX_DAILY&from_symbol=KZT&to_symbol=USD&apikey=C6ZZ86GA75MCY9AW";

    @Autowired
    public CurrencyRateService(CurrencyRateRepository currencyRateRepository) {
        this.currencyRateRepository = currencyRateRepository;
    }

    @PostConstruct
    public void init() {
        sendRequest();
    }


    public float getCurrentRateByCurrencies(CurrencyShortname currencyFrom, CurrencyShortname currencyTo) {
        return currencyRateRepository.getCurrentRateByCurrencies(currencyFrom, currencyTo);
    }

//    @Scheduled(fixedRate = 15000)
    @Scheduled(cron = "0 0 5 * * ?")
    public void scheduleRequest() {
        sendRequest();
    }

    public void sendRequest() {
        LocalDateTime date = LocalDateTime.now();
        logger.info("Getting exchanges rates for date: {}", date);
        CurrencyShortname from, to;
        from = CurrencyShortname.KZT;
        to = CurrencyShortname.USD;
//        float KZTtoUSD = ExchangeHelper.getCurrentCloseValue(from, to);
        float KZTtoUSD = 0.002F;
        CurrencyRateDTO currencyRateDTO = new CurrencyRateDTO(from, to, KZTtoUSD, date);
        CurrencyRateDTO currencyRate = modelMapper.map(currencyRateRepository.save(modelMapper.map(currencyRateDTO, CurrencyRate.class)), CurrencyRateDTO.class);
        logger.trace("Currency rate from " + from + " to " + to + " successfully saved to db: " + currencyRate);

        from = CurrencyShortname.RUB;
        to = CurrencyShortname.USD;
//        float RUBtoUSD = ExchangeHelper.getCurrentCloseValue(from, to);
        float RUBtoUSD = 0.01091F;
        currencyRateDTO = new CurrencyRateDTO(from, to, RUBtoUSD, date);
        currencyRate = modelMapper.map(currencyRateRepository.save(modelMapper.map(currencyRateDTO, CurrencyRate.class)), CurrencyRateDTO.class);
        logger.trace("Currency rate from " + from + " to " + to + " successfully saved to db: " + currencyRate);
        logger.info("Exchange rates successfully saved for date: {}", date);
    }
}
