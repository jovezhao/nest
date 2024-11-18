package com.zhaofujun.nest.ddd.event;

import java.lang.reflect.Type;

import com.zhaofujun.nest.ddd.Repository;

public abstract class EventInfoRepository implements EventInfoQuery, Repository<EventMessage<?>> {
    @Override
    public Type getEntityType() {
        return EventMessage.class;
    }
    
}
