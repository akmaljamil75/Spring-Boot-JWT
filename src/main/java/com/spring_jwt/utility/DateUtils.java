package com.spring_jwt.utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    
    public static String getDate()
    {
        LocalDate now = LocalDate.now();
        return DateFormatter(now);
    }

    public static Long getDateCurrentTimeMilles()
    {
        return System.currentTimeMillis();
    }

    private static String DateFormatter(LocalDate today)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return today.format(formatter);
    }
}
