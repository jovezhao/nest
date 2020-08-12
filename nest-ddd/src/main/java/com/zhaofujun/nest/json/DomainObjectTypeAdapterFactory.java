package com.zhaofujun.nest.json;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.google.gson.internal.bind.TreeTypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.zhaofujun.nest.core.DomainObject;

import java.lang.reflect.Field;
import java.util.List;

public class DomainObjectTypeAdapterFactory implements TypeAdapterFactory {
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (!DomainObject.class.isAssignableFrom(type.getRawType())) return null;

        try {
            Field factoriesField = Gson.class.getDeclaredField("factories");
            factoriesField.setAccessible(true);
            List<TypeAdapterFactory> factoryList = (List<TypeAdapterFactory>) factoriesField.get(gson);
            TypeAdapterFactory typeAdapterFactory = factoryList.stream()
                    .filter(p -> ReflectiveTypeAdapterFactory.class.isAssignableFrom(p.getClass()))
                    .findFirst()
                    .orElse(null);
            if (typeAdapterFactory == null) return null;

            DomainObjectTypeAdapter entityObjectAdapter = new DomainObjectTypeAdapter(gson, typeAdapterFactory);
            return new TreeTypeAdapter(entityObjectAdapter, entityObjectAdapter, gson, type, this);

        } catch (Exception ex) {
            return null;
        }

    }
}
