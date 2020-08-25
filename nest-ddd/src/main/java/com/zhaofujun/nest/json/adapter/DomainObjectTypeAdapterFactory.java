package com.zhaofujun.nest.json.adapter;

import com.google.gson.Gson;
import com.google.gson.InstanceCreator;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.bind.JsonAdapterAnnotationTypeAdapterFactory;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.google.gson.internal.bind.TreeTypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.zhaofujun.nest.context.loader.EntityCreate;
import com.zhaofujun.nest.context.model.BaseEntity;
import com.zhaofujun.nest.standard.DomainObject;
import com.zhaofujun.nest.standard.Entity;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class DomainObjectTypeAdapterFactory implements TypeAdapterFactory {

    public class EntityInstanceCreator implements InstanceCreator<BaseEntity> {
        @Override
        public BaseEntity createInstance(Type type) {
            return EntityCreate.create((Class) type, false);
        }
    }

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (!DomainObject.class.isAssignableFrom(type.getRawType())) return null;


        Map<Type, InstanceCreator<?>> instanceCreators = new HashMap<>();
        if (Entity.class.isAssignableFrom(type.getRawType()))
            instanceCreators.put(type.getType(), new EntityInstanceCreator());
        ConstructorConstructor constructorConstructor = new ConstructorConstructor(instanceCreators);
        TypeAdapterFactory typeAdapterFactory = new ReflectiveTypeAdapterFactory(constructorConstructor, gson.fieldNamingStrategy(), gson.excluder(), new JsonAdapterAnnotationTypeAdapterFactory(constructorConstructor));


        DomainObjectAdapter entityObjectAdapter = new DomainObjectAdapter(gson, typeAdapterFactory);
        return new TreeTypeAdapter(entityObjectAdapter, entityObjectAdapter, gson, type, this);


    }
}
