package com.zhaofujun.nest.manager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.zhaofujun.nest.NestConst;
import com.zhaofujun.nest.utils.lock.LockItem;
import com.zhaofujun.nest.utils.lock.LockProvider;

public class LockManager {
    private static Map<String,LockProvider> lockmMap = new HashMap<>();

    

    public static void addLockItem(LockItem cacheItem) {
        LockProvider lockProvider = ProviderManager.get(LockProvider.class, cacheItem.getProvider());
        lockmMap.put(cacheItem.getName(),lockProvider);
    }

    public static void addLockItem(Collection<LockItem> lockItems) {
        lockItems.forEach(lockItem -> {
            addLockItem(lockItem);
        });
    }

    public static LockProvider getLockProvider(String name) {
        LockProvider lockProvider = lockmMap.get(name);
        if (lockProvider == null)
            lockProvider = getLockProvider(NestConst.defaultLockProvider);
        return lockProvider;
    }
}
