package com.zhaofujun.nest.json;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

    @Override
    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
        LocalDateTime localDateTime = LocalDateTime.of(src, LocalTime.MIN);
        return new JsonPrimitive(localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }

    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        Long timestamp = json.getAsJsonPrimitive().getAsLong();
        if (0L == timestamp) return null;
        Instant instant = Instant.ofEpochMilli(timestamp);
        return LocalDate.ofInstant(instant, ZoneId.systemDefault());
    }
}
