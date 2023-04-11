package com.zhaofujun.nest3.utils;


import com.zhaofujun.nest3.model.Entity;
import com.zhaofujun.nest3.model.EntityPreStore;

import java.util.HashSet;
import java.util.Set;

public class DefaultEntityPreStore implements EntityPreStore {
    public Set<Entity> map = new HashSet<>();

    @Override
    public boolean isExist(Entity entity) {
        return map.contains(entity);
    }

    @Override
    public void add(Entity entity) {
         map.add(entity);
    }
}
