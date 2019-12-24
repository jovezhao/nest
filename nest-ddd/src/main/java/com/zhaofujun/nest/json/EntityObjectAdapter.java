package com.zhaofujun.nest.json;

import com.google.gson.*;
import com.zhaofujun.nest.context.model.Entity;
import com.zhaofujun.nest.utils.EntityUtils;

import java.lang.reflect.Type;

public class EntityObjectAdapter implements JsonSerializer<Entity>, JsonDeserializer<Entity> {
    @Override
    public Entity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        String typeName = jsonObject.get("__type__").getAsString();

        try {
            Type type = Thread.currentThread().getContextClassLoader().loadClass(typeName);
            return context.deserialize(json, type);
        } catch (ClassNotFoundException ex) {
            throw new JsonParseException("Unknown element type:" + typeName, ex);
        }

    }

    @Override
    public JsonElement serialize(Entity src, Type typeOfSrc, JsonSerializationContext context) {

        JsonElement jsonElement = context.serialize(src, src.getClass());
        String fullClassName = EntityUtils.getFullClassName(src);
        jsonElement.getAsJsonObject().addProperty("__type__", fullClassName);

        return jsonElement;
    }
}
