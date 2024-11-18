package com.zhaofujun.nest.utils.json;

import com.alibaba.fastjson2.reader.ObjectReader;
import com.alibaba.fastjson2.reader.ObjectReaderProvider;
import com.zhaofujun.nest.ddd.Entity;

import java.lang.reflect.Type;

@SuppressWarnings("rawtypes")
public class EntityObjectReaderProvider extends ObjectReaderProvider {
    EntityObjectReaderAdapter entityObjectReaderAdapter = new EntityObjectReaderAdapter();

    @SuppressWarnings("unchecked")
    @Override
    public ObjectReader getObjectReader(Type objectType, boolean fieldBased) {
        ObjectReader objectReader = super.getObjectReader(objectType, fieldBased);
        if (objectReader != null && objectReader.getObjectClass() != null
                && Entity.class.isAssignableFrom(objectReader.getObjectClass())) {
            entityObjectReaderAdapter.setObjectReader(objectReader);
            return entityObjectReaderAdapter;
        }
        return objectReader;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ObjectReader getObjectReader(long hashCode) {

        ObjectReader objectReader = super.getObjectReader(hashCode);
        if (objectReader == null)
            return null;

        if (objectReader != null && objectReader.getObjectClass() != null
                && Entity.class.isAssignableFrom(objectReader.getObjectClass())) {
            entityObjectReaderAdapter.setObjectReader(objectReader);
            return entityObjectReaderAdapter;
        }
        return objectReader;
    }
}
