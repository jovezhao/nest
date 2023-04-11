package com.zhaofujun.nest3.application.context;

import com.zhaofujun.nest3.model.Entity;
import com.zhaofujun.nest3.model.EntityPreStore;

import java.util.HashSet;
import java.util.Set;

public class EntityPreStoreList implements EntityPreStore {
    private Set<Entity> entitySet = new HashSet<>();

    @Override
    public boolean isExist(Entity entity) {
        return entitySet.contains(entity);
    }

    @Override
    public void add(Entity entity) {
        entitySet.add(entity);
    }

    public Set<Entity> getEntitySet() {
        return entitySet;
    }

    public void clear(){
        entitySet.clear();
    }
}
