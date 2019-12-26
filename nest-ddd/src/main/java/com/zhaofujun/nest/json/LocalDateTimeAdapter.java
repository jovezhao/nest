package com.zhaofujun.nest.json;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
    private String datePattern;

    public LocalDateTimeAdapter(String datePattern) {
        this.datePattern = datePattern;
    }

    @Override
    public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.format(DateTimeFormatter.ofPattern(this.datePattern)));
    }

    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        String datetime = json.getAsJsonPrimitive().getAsString();
        return LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern(this.datePattern));
    }
}
