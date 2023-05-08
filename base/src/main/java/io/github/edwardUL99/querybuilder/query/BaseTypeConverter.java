package io.github.edwardUL99.querybuilder.query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A base type converter that can be overridden and returned by implementing connections if required
 */
public class BaseTypeConverter implements TypeConverter {
    @Override
    public String string(String value) {
        return String.format("'%s'", value);
    }

    @Override
    public String number(Number number) {
        return "" + number;
    }

    @Override
    public String date(LocalDate date) {
        return string(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    @Override
    public String dateTime(LocalDateTime dateTime) {
        return string(dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}
