package com.zhaofujun.nest.json.adapter;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.zhaofujun.nest.context.loader.RepositoryEntityLoader;
import com.zhaofujun.nest.context.model.BaseEntity;
import com.zhaofujun.nest.standard.DomainObject;
import com.zhaofujun.nest.standard.EntityLoader;
import com.zhaofujun.nest.json.DomainObjectSerializeContext;
import com.zhaofujun.nest.standard.Identifier;
import com.zhaofujun.nest.utils.EntityUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class DomainObjectAdapter implements JsonSerializer<DomainObject>, JsonDeserializer<DomainObject> {


    private Gson gson;
    private TypeAdapterFactory typeAdapterFactory;

    public DomainObjectAdapter(Gson gson, TypeAdapterFactory typeAdapterFactory) {
        this.gson = gson;
        this.typeAdapterFactory = typeAdapterFactory;
    }

    @Override
    public DomainObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        JsonElement type__ = jsonObject.get("__type__");

        Class tClass = (Class) typeOfT;
        if (type__ != null) {
            try {
                tClass = Thread.currentThread().getContextClassLoader().loadClass(type__.getAsString());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        JsonElement ref__ = jsonObject.get("__ref__");//{id:"111"}
        if (ref__ != null && BaseEntity.class.isAssignableFrom(tClass)) {
            Type idClass = ((ParameterizedType) tClass.getGenericSuperclass()).getActualTypeArguments()[0];
            TypeAdapter<?> typeAdapter = typeAdapterFactory.create(gson, TypeToken.get(idClass));
            Identifier id = (Identifier) typeAdapter.fromJsonTree(ref__);

            //是实体，使用加载器加载对象
            EntityLoader entityLoader = new RepositoryEntityLoader(tClass);

            return entityLoader.create(id);//todo 读取ref中的id，根据tclass中id的类型进行赋值。
        } else {
            TypeAdapter<?> typeAdapter = typeAdapterFactory.create(gson, TypeToken.get(tClass));
            Object o = typeAdapter.fromJsonTree(json);
            return (DomainObject) o;
        }
    }

    @Override
    public JsonElement serialize(DomainObject src, Type typeOfSrc, JsonSerializationContext context) {
        if (DomainObjectSerializeContext.put(src)) {
            String fullClassName = EntityUtils.getFullClassName(src);

            //目标是一个实体，并且不是当前需要序列化的对象，进行特殊处理。
            if (BaseEntity.class.isAssignableFrom(src.getClass()) && !src.equals(DomainObjectSerializeContext.getDomainObject())) {
                BaseEntity entity = (BaseEntity) src;
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("__type__", fullClassName);
                TypeAdapter typeAdapter = typeAdapterFactory.create(gson, TypeToken.get(entity.getId().getClass()));

                jsonObject.add("__ref__", typeAdapter.toJsonTree(entity.getId()));
                return jsonObject;
            } else {
                TypeAdapter typeAdapter = typeAdapterFactory.create(gson, TypeToken.get(typeOfSrc));
                JsonElement jsonElement = typeAdapter.toJsonTree(src);//        context.serialize(src, Object.class);
                jsonElement.getAsJsonObject().addProperty("__type__", fullClassName);
                return jsonElement;
            }
        }
        return JsonNull.INSTANCE;
    }
}
