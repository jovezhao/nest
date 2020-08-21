package com.zhaofujun.nest.json.adapter;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Date;

public class DateAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {

    @Override
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getTime());    }

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        Long timestamp = json.getAsJsonPrimitive().getAsLong();
        if (0L == timestamp) return null;
        return new Date(timestamp);
    }
}


