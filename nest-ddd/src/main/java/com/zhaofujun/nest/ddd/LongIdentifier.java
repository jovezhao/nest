package com.zhaofujun.nest.ddd;

/**
 * 长整型标识符类。
 */
public class LongIdentifier extends Identifier {

    /**
     * 标识符值。
     */
    private long id;

    /**
     * 获取标识符值。
     *
     * @return 标识符值
     */
    public long getId() {
        return id;
    }

    /**
     * 构造函数。
     *
     * @param id 标识符值
     */
    public LongIdentifier(long id) {
        this.id = id;
    }

    @Override
    /**
     * 将标识符转换为字符串值。
     *
     * @return 字符串值
     */
    public String toValue() {
        return String.valueOf(this.id);
    }

}
