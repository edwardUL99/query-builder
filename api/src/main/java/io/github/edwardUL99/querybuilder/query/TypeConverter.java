package io.github.edwardUL99.querybuilder.query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an interface for converting types
 */
public interface TypeConverter {
    /**
     * Quotes the given string value
     * @param value the value to convert
     * @return the quoted string
     */
    String string(String value);

    /**
     * Convert the given number to a string
     * @param number the number to convert
     * @return the converted number
     */
    String number(Number number);

    /**
     * Convert the given date to database format
     * @param date the date to convert
     * @return the converted date
     */
    String date(LocalDate date);

    /**
     * Convert a date of format yyyy-MM-dd to database date
     * @param date the date to convert
     * @return the converted date
     */
    default String date(String date) {
        return date(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    /**
     * Convert the given date to database format
     * @param dateTime the date time to convert
     * @return the converted date time
     */
    String dateTime(LocalDateTime dateTime);

    /**
     * Convert a date of format yyyy-MM-dd HH:mm:ss to database date
     * @param dateTime the date to convert
     * @return the converted date
     */
    default String dateTime(String dateTime) {
        return dateTime(LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}
