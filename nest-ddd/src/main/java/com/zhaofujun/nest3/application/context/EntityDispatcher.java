package com.zhaofujun.nest3.application.context;

import com.zhaofujun.nest3.model.AggregateRoot;
import com.zhaofujun.nest3.model.Entity;
import com.zhaofujun.nest3.model.Identifier;

import java.util.*;
import java.util.function.Consumer;


public interface EntityDispatcher {
    void initRoot(AggregateRoot aggregateRoot);

    void endRoot(AggregateRoot aggregateRoot);

    void dispatch();

    void setBeforeExecuteCallback(Consumer<Set<Entity>> beforeExecuteCallback);

    void setExecutedCallback(Consumer<Set<Entity>> executeCallback);

    void setCommittedCallback(Consumer<Set<Entity>> committedCallback);

    void setFailCallback(Consumer<Set<Entity>> failCallback);

    <U extends Entity> U getEntity(Class<U> uClass, Identifier id);
}


