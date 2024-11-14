package com.zhaofujun.nest.utils.json;

import com.alibaba.fastjson2.writer.ObjectWriter;
import com.alibaba.fastjson2.writer.ObjectWriterProvider;
import com.zhaofujun.nest.ddd.Entity;

import java.lang.reflect.Type;

public class EntityObjectWriterProvider extends ObjectWriterProvider {

    EntityObjectWriterAdapter objectWriterAdapter = new EntityObjectWriterAdapter();

    @Override
    public ObjectWriter getObjectWriter(Type objectType, Class objectClass, boolean fieldBased) {
        ObjectWriter objectWriter = super.getObjectWriter(objectType, objectClass, fieldBased);
        if (Entity.class.isAssignableFrom(objectClass)) {
            objectWriterAdapter.setObjectWriter(objectWriter);
            return objectWriterAdapter;
        }
        return objectWriter;
    }
}
