package com.zhaofujun.nest3.impl.fastjson;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.reader.ObjectReader;
import com.zhaofujun.nest3.application.NestApplication;
import com.zhaofujun.nest3.application.loader.RepositoryEntityLoader;
import com.zhaofujun.nest3.model.Entity;
import com.zhaofujun.nest3.model.EntityLoader;
import com.zhaofujun.nest3.model.Identifier;

import java.lang.reflect.Type;

public class EntityObjectReaderAdapter implements ObjectReader<Entity> {

    private ObjectReader objectReader;

    public EntityObjectReaderAdapter(ObjectReader objectReader) {
        this.objectReader = objectReader;
    }

    @Override
    public Entity readObject(JSONReader jsonReader, Type fieldType, Object fieldName, long features) {
        Entity entity = null;

        JSONObject objectMap = (JSONObject) jsonReader.readObject();
//        try (JSONReader entityReader = JSONReader.of(jsonReader.getContext(), objectMap.toString())) {
//            entity = (Entity) objectReader.readObject(entityReader, fieldType, fieldName, features);
//        }

        if (objectMap.containsKey("__shorthand__") && NestApplication.current() != null) {
            //有发现短字段，并且应用程序已经存在，使用应用程序的仓库加载实体
            String id = objectMap.get("id").toString();
            Identifier identifier = objectMap.getObject("id", Identifier.class, JSONReader.Feature.FieldBased, JSONReader.Feature.SupportAutoType);

            EntityLoader entityLoader = new RepositoryEntityLoader(objectReader.getObjectClass());// EntityLoaderFactory.create(tClass, EntityLoaderType.RepositoryEntityLoader);
            entity = entityLoader.create(identifier);


        } else {
            try (JSONReader entityReader = JSONReader.of(jsonReader.getContext(), objectMap.toString())) {
                entity = (Entity) objectReader.readObject(entityReader, fieldType, fieldName, features);
            }
        }
        return entity;
    }
}
