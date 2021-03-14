package com.zhaofujun.nest.utils;

import com.zhaofujun.nest.lock.LockProvider;
import com.zhaofujun.nest.lock.LockProviderFactory;


public class LockUtils {
    public static boolean runByLock(String key, Runnable runnable) {
        LockProvider lockProvider = LockProviderFactory.create();

        for (int i = 0; i < 3; i++) {
            String requestId= lockProvider.lock(key) ;
            if (!StringUtils.isEmpty(requestId)) {
                try {
                    runnable.run();
                } finally {
                    lockProvider.unlock(key,requestId);
                    return true;
                }
            }
        }
        return false;
    }
}

