package com.zhaofujun.nest.ddd.context;

import java.util.HashSet;
import java.util.Set;

import com.zhaofujun.nest.ddd.Entity;

public class QueryContext {

    private Set<Entity> entities = new HashSet<>();

    public void begin() {
        entities.clear();
    }

    public void submit() {

        // 将查询上下文的实体转移到服务上下文中
        // 让查询上下文中产生的对象都转换为准备状态
        ServiceContext serviceContext = ServiceContextManager.getCurrentContext();
        if (serviceContext != null) {
            entities.forEach(entity -> {
                entity._ready();
                serviceContext.addEntity(entity);
            });
        }

    }

    public void end() {
        entities.clear();
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }
}
