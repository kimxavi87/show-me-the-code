package com.kimxavi87.spring.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {
    private static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static String DATE_FORMAT = "yyyyMMdd";

    public static long getCurrentTimestamp() {
        return Instant.now().getEpochSecond();
    }

    public static int convertTimeFormatToTimestamp(String time) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT);
        Date date = dateFormat.parse(time);
        return (int) (date.getTime() / 1000);
    }

    public static String getTodayTime() {
        DateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT);
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getAMonthAgoTime() {
        DateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        Date date = calendar.getTime();
        return dateFormat.format(date);
    }

    public static String getTodayDate() {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        Date date = new Date();
        return dateFormat.format(date);
    }
}
