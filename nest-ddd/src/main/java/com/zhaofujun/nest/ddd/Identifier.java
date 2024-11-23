package com.zhaofujun.nest.ddd;

/**
 * 标识符抽象类。
 */
public abstract class Identifier extends ValueObject {
    /**
     * 将标识符转换为字符串值。
     *
     * @return 字符串值
     */
    public abstract String toValue();

    @Override
    protected Object[] getPropertiesForComparison() {
        return new Object[] { toValue() };
    }
}
