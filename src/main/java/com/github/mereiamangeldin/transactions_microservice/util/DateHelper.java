package com.github.mereiamangeldin.transactions_microservice.util;

import java.time.LocalDateTime;
import java.util.Date;

public class DateHelper {
    public static String getMonthYear(LocalDateTime date) {
        return String.format("%02d-%04d", date.getMonth().getValue(), date.getYear());
    }
}
