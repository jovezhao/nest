package com.zhaofujun.nest3.impl.fastjson;

import com.alibaba.fastjson2.writer.ObjectWriter;
import com.alibaba.fastjson2.writer.ObjectWriterProvider;
import com.zhaofujun.nest3.model.Entity;
import com.zhaofujun.nest3.model.JsonCallback;

import java.lang.reflect.Type;

public class EntityObjectWriterProvider extends ObjectWriterProvider {

    private JsonCallback<Entity> jsonCallback;

    public EntityObjectWriterProvider(JsonCallback<Entity> jsonCallback){
        this.jsonCallback=jsonCallback;
    }

    @Override
    public ObjectWriter getObjectWriter(Type objectType, Class objectClass, boolean fieldBased) {
        ObjectWriter objectWriter = super.getObjectWriter(objectType, objectClass, fieldBased);
        if (Entity.class.isAssignableFrom(objectClass)) {
            EntityObjectWriterAdapter objectWriterAdapter = new EntityObjectWriterAdapter(objectWriter,jsonCallback);
            return objectWriterAdapter;
        }
        return objectWriter;
    }
}
