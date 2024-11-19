package com.zhaofujun.nest.ddd;

/**
 * 值对象抽象类，继承自 DomainObject 类。
 */
public abstract class ValueObject extends DomainObject {
    /**
     * 重写 equals 方法，用于比较两个值对象是否相等。
     *
     * @param obj 要比较的对象
     * @return true 如果两个对象相等，false 否则
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ValueObject)) return false;
        if (!this.getClass().equals(obj.getClass())) return false;

        return this.hashCode() == obj.hashCode();
    }
}
