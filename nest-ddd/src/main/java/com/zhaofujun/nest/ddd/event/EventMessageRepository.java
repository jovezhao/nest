package com.zhaofujun.nest.ddd.event;

import java.lang.reflect.Type;

import com.zhaofujun.nest.ddd.Repository;

public abstract class EventMessageRepository implements EventMessageQuery, Repository<EventMessageModel<?>> {
    @Override
    public Type getEntityType() {
        return EventMessageModel.class;
    }
    
}
