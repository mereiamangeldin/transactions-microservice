package com.github.mereiamangeldin.transactions_microservice.util.exchangeHelper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeSeriesFX {

    @JsonProperty("Time Series FX (Daily)")
    private Map<String, DailyData> dailyData;

}
