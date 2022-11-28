package ru.practicum.ewm.util;

import java.time.format.DateTimeFormatter;

public class Const {

    public static final String SIZE_OF_PAGE = "10";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

    public static final int NUMBER_HOUR_BEFORE_START_FOR_ADMIN = 1;

    public static final int NUMBER_HOUR_BEFORE_START_FOR_USER = 2;

}