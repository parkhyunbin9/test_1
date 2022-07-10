package com.reco.prob.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {

    public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";
    public static final String SIMPLE_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final DateTimeFormatter SIMPLE_DATE_FORMATTER = DateTimeFormatter.ofPattern(SIMPLE_DATE_FORMAT);
    public static final DateTimeFormatter SIMPLE_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(SIMPLE_DATE_TIME_FORMAT);

    public static LocalDate getLocalDateWithSimpleFormat(LocalDate localDate) {
        return LocalDate.parse(localDate.format(SIMPLE_DATE_FORMATTER), SIMPLE_DATE_FORMATTER);
    }

    public static LocalDate getLocalDate(String localDate) {
        return LocalDate.parse(localDate, SIMPLE_DATE_FORMATTER);
    }

    public static LocalDateTime convertLocalDateToLocalDateTime(LocalDate localDate) {
        return LocalDateTime.parse(localDate.format(SIMPLE_DATE_TIME_FORMATTER), SIMPLE_DATE_TIME_FORMATTER);
    }

    public static LocalDateTime getLocalDateTimeWithSimpleFormat(LocalDateTime localDateTime) {
        return LocalDateTime.parse(localDateTime.format(SIMPLE_DATE_TIME_FORMATTER), SIMPLE_DATE_TIME_FORMATTER);
    }

    public static LocalDate convertLocalDateTimeToLocalDateTime(LocalDateTime localDateTime) {
        return LocalDate.parse(localDateTime.format(SIMPLE_DATE_FORMATTER), SIMPLE_DATE_FORMATTER);
    }

    public static LocalDateTime getLocalDateTime (String localDateTime) {
        return LocalDateTime.parse(localDateTime, SIMPLE_DATE_TIME_FORMATTER);
    }
}
