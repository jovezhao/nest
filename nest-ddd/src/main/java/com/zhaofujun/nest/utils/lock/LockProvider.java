package com.zhaofujun.nest.utils.lock;

import com.zhaofujun.nest.provider.Provider;

/**
 * 锁提供者
 */
public interface LockProvider extends Provider {
    void lock(String name, Runnable runnable);
}
