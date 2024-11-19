package com.zhaofujun.nest.ddd;

/**
 * 字符串标识符类，继承自 Identifier 类。
 */
public class StringIdentifier extends Identifier {

    /**
     * 标识符值。
     */
    private String id;

    /**
     * 构造函数，初始化标识符值。
     *
     * @param id 标识符值
     */
    public StringIdentifier(String id) {
        this.id = id;
    }

    /**
     * 将标识符值转换为字符串。
     *
     * @return 标识符值字符串
     */
    @Override
    public String toValue() {
        return id.toString();
    }

    /**
     * 获取标识符值。
     *
     * @return 标识符值
     */
    public String getId() {
        return id;
    }
}
