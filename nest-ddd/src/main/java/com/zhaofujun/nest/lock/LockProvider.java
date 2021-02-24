package com.zhaofujun.nest.lock;

import com.zhaofujun.nest.provider.Provider;

public interface LockProvider extends Provider {
    boolean lock(String key);

    void unlock(String key);
}
