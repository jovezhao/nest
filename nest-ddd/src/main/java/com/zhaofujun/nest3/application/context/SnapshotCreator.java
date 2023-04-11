package com.zhaofujun.nest3.application.context;


import com.zhaofujun.nest3.model.Entity;
import com.zhaofujun.nest3.model.EntityIterator;
import com.zhaofujun.nest3.model.EntityJsonBuilder;
import com.zhaofujun.nest3.model.EntityPreStore;
import com.zhaofujun.nest3.model.JsonCallback;


public class SnapshotCreator {
    EntityIterator entityFinder = null;
    EntityJsonBuilder entityJsonBuilder = null;
    EntityPreStore entityPreStore = null;

    public SnapshotCreator(EntityIterator entityFinder, EntityJsonBuilder entityJsonBuilder, EntityPreStore entityPreStore) {
        this.entityFinder = entityFinder;
        this.entityJsonBuilder = entityJsonBuilder;
        this.entityPreStore = entityPreStore;
    }

    public void each(Entity entity, JsonCallback jsonCallback) {
        entityFinder.each(entity, p -> {
            if (!entityPreStore.isExist(p)) {
                entityPreStore.add(p);
                String jsonString = entityJsonBuilder.toJsonString(p);
                jsonCallback.callback(p, jsonString);
            }
        });
    }
}

