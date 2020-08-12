package com.zhaofujun.nest.json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.zhaofujun.nest.core.DomainObject;
import com.zhaofujun.nest.utils.EntityUtils;

import java.lang.reflect.Type;

public class DomainObjectTypeAdapter implements JsonSerializer<DomainObject>, JsonDeserializer<DomainObject> {


    private Gson gson;
    private TypeAdapterFactory typeAdapterFactory;

    public DomainObjectTypeAdapter(Gson gson, TypeAdapterFactory typeAdapterFactory) {
        this.gson = gson;
        this.typeAdapterFactory = typeAdapterFactory;
    }

    @Override
    public DomainObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        JsonElement type__ = jsonObject.get("__type__");

        try {
            Type type = type__ == null ? typeOfT : Thread.currentThread().getContextClassLoader().loadClass(type__.getAsString());
            TypeAdapter<?> typeAdapter = typeAdapterFactory.create(gson, TypeToken.get(type));
            Object o = typeAdapter.fromJsonTree(json);
//            return context.deserialize(json, type);
            return (DomainObject) o;
        } catch (ClassNotFoundException ex) {
            throw new JsonParseException("Unknown element type:" + type__.getAsString(), ex);
        }

    }

    @Override
    public JsonElement serialize(DomainObject src, Type typeOfSrc, JsonSerializationContext context) {
        if (DomainObjectSerializeContext.put(src)) {
            TypeAdapter typeAdapter = typeAdapterFactory.create(gson, TypeToken.get(typeOfSrc));
            JsonElement jsonElement = typeAdapter.toJsonTree(src);//        context.serialize(src, Object.class);
            String fullClassName = EntityUtils.getFullClassName(src);
            jsonElement.getAsJsonObject().addProperty("__type__", fullClassName);
            return jsonElement;
        }
        return JsonNull.INSTANCE;
    }
}
