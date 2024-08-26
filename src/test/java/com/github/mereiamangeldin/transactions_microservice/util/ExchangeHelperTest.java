package com.github.mereiamangeldin.transactions_microservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mereiamangeldin.transactions_microservice.enums.CurrencyShortname;
import com.github.mereiamangeldin.transactions_microservice.util.exchangeHelper.ExchangeHelper;
import com.github.mereiamangeldin.transactions_microservice.util.exchangeHelper.TimeSeriesFX;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ExchangeHelperTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ExchangeHelper exchangeHelper;

    private final String jsonTemplate = "{\n" +
            "    \"Meta Data\": {\n" +
            "        \"1. Information\": \"Forex Daily Prices (open, high, low, close)\",\n" +
            "        \"2. From Symbol\": \"KZT\",\n" +
            "        \"3. To Symbol\": \"USD\",\n" +
            "        \"4. Output Size\": \"Compact\",\n" +
            "        \"5. Last Refreshed\": \"2024-08-23\",\n" +
            "        \"6. Time Zone\": \"UTC\"\n" +
            "    },\n" +
            "    \"Time Series FX (Daily)\": {\n" +
            "        \"2024-08-23\": {\n" +
            "            \"1. open\": \"0.00208\",\n" +
            "            \"2. high\": \"0.00208\",\n" +
            "            \"3. low\": \"0.00206\",\n" +
            "            \"4. close\": \"0.00200\"\n" +
            "        },\n" +
            "        \"2024-08-22\": {\n" +
            "            \"1. open\": \"0.00209\",\n" +
            "            \"2. high\": \"0.00209\",\n" +
            "            \"3. low\": \"0.00208\",\n" +
            "            \"4. close\": \"0.00208\"\n" +
            "        }\n" +
            "    }\n" +
            "}";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCurrentCloseValue() throws JsonProcessingException {
        when(restTemplate.getForObject("/currecies-api", String.class)).thenReturn(jsonTemplate);
        TimeSeriesFX mockTimeSeriesFX = new TimeSeriesFX();
        when(objectMapper.readValue(jsonTemplate, TimeSeriesFX.class)).thenReturn(mockTimeSeriesFX);

        float expectedCloseValue = 0.00200f;

        float result = exchangeHelper.getCurrentCloseValue(CurrencyShortname.KZT, CurrencyShortname.USD);

        assertEquals(expectedCloseValue, result, 0.00001);
    }
}