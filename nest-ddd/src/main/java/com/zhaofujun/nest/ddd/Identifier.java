package com.zhaofujun.nest.ddd;

import java.io.Serializable;

/**
 * 标识符抽象类。
 */
public abstract class Identifier implements Serializable {
    /**
     * 将标识符转换为字符串值。
     *
     * @return 字符串值
     */
    public abstract String toValue();

    @Override
    /**
     * 比较两个标识符是否相等。
     *
     * @param obj 要比较的对象
     * @return 是否相等
     */
    public boolean equals(Object obj) {
        if (Identifier.class.isInstance(obj)) {
            Identifier identifier = (Identifier) obj;
            return this.toValue().equals(identifier.toValue());
        }
        return false;
    }

    @Override
    /**
     * 获取标识符的哈希码。
     *
     * @return 哈希码
     */
    public int hashCode() {
        return ("!@#$%^" + toValue() + "(*&").hashCode();
    }
}
