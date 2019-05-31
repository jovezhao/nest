package com.guoshouxiang.nest.context.repository;

import com.guoshouxiang.nest.context.model.BaseEntity;
import com.guoshouxiang.nest.context.model.Identifier;
import com.guoshouxiang.nest.context.loader.EntityLoader;

import java.util.HashMap;
import java.util.Map;

public class DefaultRepository implements Repository<BaseEntity> {

    private static Map<Identifier, BaseEntity> entityMap = new HashMap<>();



    @Override
    public BaseEntity getEntityById(Identifier identifier, EntityLoader entityLoader) {
        return entityMap.get(identifier);
    }

    @Override
    public Class<BaseEntity> getEntityClass() {
        return BaseEntity.class;
    }

    @Override
    public void save(BaseEntity baseEntity) {
        entityMap.put(baseEntity.getId(), baseEntity);
    }

    @Override
    public void remove(BaseEntity baseEntity) {
        entityMap.remove(baseEntity.getId());
    }
}
