package com.zhaofujun.nest.lock;

import com.zhaofujun.nest.provider.Provider;

public interface LockProvider extends Provider {
    String lock(String key);

    void unlock(String key,String requestId);
}
