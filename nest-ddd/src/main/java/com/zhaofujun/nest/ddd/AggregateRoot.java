package com.zhaofujun.nest.ddd;

/**
 * 表示聚合根的抽象类，聚合根是领域驱动设计（DDD）中的一个模式。
 * 聚合根是一种特殊的实体，它作为一组相关对象的入口点，这些对象一起形成一个聚合。
 * 本抽象类主要定义了聚合根标识符的泛型类型。
 *
 * @param <T> 标识符的类型，必须扩展Identifier类。
 * 
 * @author zhaofujun
 * 
 */
public abstract class AggregateRoot<T extends Identifier> extends Entity<T> {

    /**
     * 构造聚合根实例。
     *
     * @param id 聚合根的标识符，不能为null。
     * 
     * @throws NullPointerException 如果id为null
     */
    public AggregateRoot(T id) {
        super(id);
    }
}
