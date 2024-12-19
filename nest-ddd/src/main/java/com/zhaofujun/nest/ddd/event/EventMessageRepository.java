package com.zhaofujun.nest.ddd.event;

import java.lang.reflect.Type;

import com.zhaofujun.nest.ddd.LongIdentifier;
import com.zhaofujun.nest.ddd.Repository;

public abstract class EventMessageRepository implements EventMessageQuery, Repository<EventMessageModel<?>, LongIdentifier> {
    @Override
    public Class getEntityClass() {
        return EventMessageModel.class;
    }
}
