package com.zhaofujun.nest3.model;

import java.util.function.Consumer;

/**
 * 实体遍历器，传入一个聚合时，可以遍历查找实体，通过回调方法通知对实体的操作。
 */
public interface EntityIterator {
    void each(Entity entityRoot, Consumer<Entity> consumer);
}
