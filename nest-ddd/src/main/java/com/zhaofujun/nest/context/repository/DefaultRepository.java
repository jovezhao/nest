package com.zhaofujun.nest.context.repository;

import com.zhaofujun.nest.context.model.BaseEntity;
import com.zhaofujun.nest.context.model.AbstractIdentifier;
import com.zhaofujun.nest.standard.EntityLoader;
import com.zhaofujun.nest.json.JsonCreator;
import com.zhaofujun.nest.standard.Identifier;
import com.zhaofujun.nest.standard.Repository;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class DefaultRepository implements Repository<BaseEntity> {

    private JsonCreator jsonCreator = JsonCreator.getInstance();

    class EntityItem {
        private AbstractIdentifier id;
        private Class tClass;
        private String value;
    }

    private static Map<Identifier, EntityItem> entityMap = new HashMap<>();

    @Override
    public BaseEntity getEntityById(Identifier abstractIdentifier, EntityLoader<BaseEntity> entityLoader) {
        EntityItem entityItem = entityMap.get(abstractIdentifier);
        System.out.println("get:" + entityItem.value);
        if (entityItem != null) {
            return jsonCreator.toObj(entityItem.value, (Type) entityItem.tClass.getSuperclass());
        }
        return null;
    }

    @Override
    public Class<BaseEntity> getEntityClass() {
        return BaseEntity.class;
    }


    @Override
    public void insert(BaseEntity entity) {
        EntityItem entityItem = new EntityItem();
        entityItem.id = entity.getId();
        entityItem.tClass = entity.getClass();
        entityItem.value = jsonCreator.toJsonString(entity);
        entityMap.put(entity.getId(), entityItem);
        System.out.println("insert:" + entityItem.value);

    }

    @Override
    public void update(BaseEntity entity) {
        EntityItem entityItem = new EntityItem();
        entityItem.id = entity.getId();
        entityItem.tClass = entity.getClass();
        entityItem.value = jsonCreator.toJsonString(entity);
        entityMap.put(entity.getId(), entityItem);
        System.out.println("update:" + entityItem.value);

    }

    @Override
    public void delete(BaseEntity entity) {
        entityMap.remove(entity.getId());
        System.out.println("delete:");

    }
}
