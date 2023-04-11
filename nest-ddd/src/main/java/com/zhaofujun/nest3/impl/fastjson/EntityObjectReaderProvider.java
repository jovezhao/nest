package com.zhaofujun.nest3.impl.fastjson;

import com.alibaba.fastjson2.reader.ObjectReader;
import com.alibaba.fastjson2.reader.ObjectReaderProvider;
import com.zhaofujun.nest3.model.Entity;

import java.lang.reflect.Type;

public class EntityObjectReaderProvider extends ObjectReaderProvider {

    @Override
    public ObjectReader getObjectReader(Type objectType, boolean fieldBased) {
        ObjectReader objectReader = super.getObjectReader(objectType, fieldBased);
        if(Entity.class.isAssignableFrom( objectReader.getObjectClass())){
            EntityObjectReaderAdapter entityObjectReaderAdapter = new EntityObjectReaderAdapter(objectReader);
            return entityObjectReaderAdapter;
        }
        return objectReader;
    }

    @Override
    public ObjectReader getObjectReader(long hashCode) {
        ObjectReader objectReader = super.getObjectReader(hashCode);

        if(objectReader!=null && objectReader.getObjectClass()!=null && Entity.class.isAssignableFrom( objectReader.getObjectClass())){
            EntityObjectReaderAdapter entityObjectReaderAdapter = new EntityObjectReaderAdapter(objectReader);
            return entityObjectReaderAdapter;
        }
        return objectReader;
    }
}
