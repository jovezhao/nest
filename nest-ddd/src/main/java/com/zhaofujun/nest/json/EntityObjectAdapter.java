package com.zhaofujun.nest.json;

import com.google.gson.*;
import com.zhaofujun.nest.context.model.Entity;

import java.lang.reflect.Type;

public class EntityObjectAdapter implements JsonSerializer<Entity>, JsonDeserializer<Entity> {
    @Override
    public Entity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");

        try {
            return context.deserialize(element, Class.forName(type));
        } catch (ClassNotFoundException ex) {
            throw new JsonParseException("Unknown element type:" + type, ex);
        }

    }

    @Override
    public JsonElement serialize(Entity src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(src.getClass().getName()));
        result.add("properties", context.serialize(src, src.getClass()));
        return result;
    }
}
