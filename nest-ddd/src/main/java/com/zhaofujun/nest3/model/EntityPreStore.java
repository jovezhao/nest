package com.zhaofujun.nest3.model;

public interface EntityPreStore {

    boolean isExist(Entity entity);

    /**
     * 如果已存在，返回false,如果不存在，返回true
     *
     * @param entity
     * @return
     */
    void add(Entity entity);
}
