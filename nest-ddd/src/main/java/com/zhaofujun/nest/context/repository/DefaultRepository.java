package com.zhaofujun.nest.context.repository;

import com.zhaofujun.nest.context.model.Entity;
import com.zhaofujun.nest.core.Identifier;
import com.zhaofujun.nest.core.EntityLoader;
import com.zhaofujun.nest.core.Repository;

import java.util.HashMap;
import java.util.Map;

public class DefaultRepository implements Repository<Entity> {

    private static Map<Identifier, Entity> entityMap = new HashMap<>();



    @Override
    public Entity getEntityById(Identifier identifier, EntityLoader entityLoader) {
        return entityMap.get(identifier);
    }

    @Override
    public Class<Entity> getEntityClass() {
        return Entity.class;
    }

    @Override
    public void insert(Entity entity) {
        entityMap.put(entity.getId(), entity);
    }

    @Override
    public void update(Entity entity) {
        entityMap.put(entity.getId(), entity);
    }

    @Override
    public void remove(Entity entity) {
        entityMap.remove(entity.getId());
    }
}
