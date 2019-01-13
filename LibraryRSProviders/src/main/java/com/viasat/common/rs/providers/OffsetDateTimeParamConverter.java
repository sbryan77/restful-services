package com.viasat.common.rs.providers;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import javax.ws.rs.ext.ParamConverter;

public class OffsetDateTimeParamConverter implements ParamConverter<OffsetDateTime> {
    public OffsetDateTimeParamConverter() {
    }

    public OffsetDateTime fromString(String value) {
        return OffsetDateTime.parse(value, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public String toString(OffsetDateTime value) {
        return value.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}
