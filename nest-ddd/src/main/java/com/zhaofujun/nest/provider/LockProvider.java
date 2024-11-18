package com.zhaofujun.nest.provider;

import java.util.function.Supplier;

/**
 * 锁提供者
 */
public interface LockProvider extends Provider {
    <T> T lock(String name, Supplier<T> runnable);
}
