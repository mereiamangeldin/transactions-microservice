package com.github.mereiamangeldin.transactions_microservice.util.exchangeHelper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mereiamangeldin.transactions_microservice.enums.CurrencyShortname;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

public class ExchangeHelper {
    static final float defaultRUBtoUSD = 0.011F;
    static final float defaultKZTtoUSD = 0.0021F;

    static final String exceededRequest = "{\n" +
            "    \"Information\": \"Thank you for using Alpha Vantage! Our standard API rate limit is 25 requests per day. Please subscribe to any of the premium plans at https://www.alphavantage.co/premium/ to instantly remove all daily rate limits.\"\n" +
            "}";
    static final String url = "https://www.alphavantage.co/query?function=FX_DAILY&from_symbol=%s&to_symbol=%s&apikey=%s";
    @Value("${api.key}")

    private static String apiKey;

    public static float getCurrentCloseValue(CurrencyShortname currencyFrom, CurrencyShortname currencyTo){
        String updUrl = String.format(url, currencyFrom, currencyTo, apiKey);
        RestTemplate restTemplate = new RestTemplate();
        String json = restTemplate.getForObject(updUrl, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        float result = 0;
        assert json != null;
        if (json.equals(exceededRequest)){
            if(currencyTo.equals(CurrencyShortname.USD)){
                if(currencyFrom.equals(CurrencyShortname.RUB)) {
                    return defaultRUBtoUSD;
                }
                else if(currencyFrom.equals(CurrencyShortname.KZT)){
                    return defaultKZTtoUSD;
                }
            }
        }
        try {
            TimeSeriesFX timeSeriesFX = objectMapper.readValue(json, TimeSeriesFX.class);
            String firstDateKey = timeSeriesFX.getDailyData().keySet().iterator().next();
            String closeValue = timeSeriesFX.getDailyData().get(firstDateKey).getClose();
            result = Float.parseFloat(closeValue);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
