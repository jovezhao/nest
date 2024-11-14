package com.zhaofujun.nest.ddd.event;

import com.zhaofujun.nest.ddd.Repository;

public abstract class EventInfoRepostory implements EventInfoQuery, Repository<EventMessage> {
    @Override
    public Class<EventMessage> getEntityClass() {
        return EventMessage.class;
    }
    
}
