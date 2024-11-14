package com.zhaofujun.nest.utils.json;

import java.lang.reflect.Type;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.reader.ObjectReader;
import com.zhaofujun.nest.ddd.Entity;
import com.zhaofujun.nest.ddd.Identifier;
import com.zhaofujun.nest.ddd.context.EntityLoader;

public class EntityObjectReaderAdapter implements ObjectReader<Entity> {

    private ObjectReader objectReader;

    public void setObjectReader(ObjectReader objectReader) {
        this.objectReader = objectReader;
    }

    @Override
    public Entity readObject(JSONReader jsonReader, Type fieldType, Object fieldName, long features) {

        Entity entity = null;
        JSONObject objectMap = (JSONObject) jsonReader.readObject();
        try (JSONReader entityReader = JSONReader.of(jsonReader.getContext(), objectMap.toString())) {
            if (!objectMap.containsKey("__shorthand__")) {
                entity = (Entity) objectReader.readObject(entityReader, fieldType, fieldName,
                        features);
            } else {
                String idTypeName = objectMap.getJSONObject("id").getString("@type");
                Identifier identifier = (Identifier) objectMap.getObject("id", new NameType(idTypeName),
                        JSONReader.Feature.FieldBased, JSONReader.Feature.SupportAutoType);
                entity = EntityLoader.load(objectReader.getObjectClass(), identifier);
            }
        }

        return entity;
    }
}

class NameType implements Type {
    private String name;

    public NameType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
