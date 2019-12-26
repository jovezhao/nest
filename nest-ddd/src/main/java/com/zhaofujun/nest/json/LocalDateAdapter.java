package com.zhaofujun.nest.json;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
    private String datePattern;

    public LocalDateAdapter(String datePattern) {
        this.datePattern = datePattern;
    }

    @Override
    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.format(DateTimeFormatter.ofPattern(this.datePattern)));
    }

    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        String datetime = json.getAsJsonPrimitive().getAsString();
        return LocalDate.parse(datetime, DateTimeFormatter.ofPattern(this.datePattern));
    }
}
