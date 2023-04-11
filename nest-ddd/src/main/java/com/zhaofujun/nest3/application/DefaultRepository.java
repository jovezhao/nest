package com.zhaofujun.nest3.application;


import com.zhaofujun.nest3.model.Entity;
import com.zhaofujun.nest3.model.Identifier;
import com.zhaofujun.nest3.model.Repository;

public class DefaultRepository implements Repository<Entity> {


    @Override
    public Class<Entity> getEntityClass() {
        return Entity.class;
    }

    @Override
    public Entity getEntityById(Identifier Identifier) {
        return null;
    }

    @Override
    public void insert(Entity entity) {

    }

    @Override
    public void update(Entity entity) {

    }

    @Override
    public void delete(Entity entity) {

    }
}
