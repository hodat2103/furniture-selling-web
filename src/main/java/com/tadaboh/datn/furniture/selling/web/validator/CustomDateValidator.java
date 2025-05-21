package com.tadaboh.datn.furniture.selling.web.validator;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomDateValidator extends JsonDeserializer<LocalDateTime> {
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("ss:mm:HH dd/MM/yyyy");

    /**
     * Deserializes a JSON date string into a {@link LocalDateTime} object.
     * <p>
     * This method parses the date string using the predefined formatter {@code INPUT_FORMATTER}.
     * If the JSON field name is "start_date", the seconds and nanoseconds are set to 0.
     * If the JSON field name is "end_date", the seconds are set to 59 and nanoseconds to 0.
     * </p>
     *
     * @param p     the JSON parser providing the date string
     * @param ctxt  the deserialization context
     * @return      a {@link LocalDateTime} object with adjusted second and nanosecond values if applicable
     * @throws IOException if there is an error reading the JSON input
     */

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String dateString = p.getText();
        LocalDateTime dateTime = LocalDateTime.parse(dateString, INPUT_FORMATTER);

        if ("start_date".equals(p.getCurrentName())) {
            return dateTime.withSecond(0).withNano(0);
        } else if ("end_date".equals(p.getCurrentName())) {
            return dateTime.withSecond(59).withNano(0);
        }
        return dateTime;
    }

    public static String formatLocalDateTime(LocalDateTime dateTime) {
        return dateTime.format(OUTPUT_FORMATTER);
    }
}
