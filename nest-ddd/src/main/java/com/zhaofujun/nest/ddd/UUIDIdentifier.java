package com.zhaofujun.nest.ddd;

import java.util.UUID;

/**
 * UUID 标识符类，继承自 Identifier 类。
 */
public class UUIDIdentifier extends Identifier {
    /**
     * UUID 值。
     */
    private UUID id;

    /**
     * 将 UUID 值转换为字符串。
     *
     * @return UUID 值字符串
     */
    @Override
    public String toValue() {
        return id.toString();
    }

    /**
     * 构造函数，初始化 UUID 值。
     *
     * @param id UUID 值
     */
    public UUIDIdentifier(UUID id) {
        this.id = id;
    }

    /**
     * 创建一个新的 UUID 标识符。
     *
     * @return 新的 UUID 标识符
     */
    private static UUIDIdentifier newId() {
        return new UUIDIdentifier(UUID.randomUUID());
    }
}
