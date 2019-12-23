package com.zhaofujun.nest.json;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.bind.TreeTypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.zhaofujun.nest.context.model.Entity;

import java.lang.reflect.Modifier;

public class EntityTypeAdapterFactory implements TypeAdapterFactory {
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (!Entity.class.isAssignableFrom(type.getRawType())) return null;

        if (!Modifier.isAbstract(type.getRawType().getModifiers())) return null;

        EntityObjectAdapter entityObjectAdapter = new EntityObjectAdapter();
        return new TreeTypeAdapter(entityObjectAdapter, entityObjectAdapter, gson, type, null);


    }
}
