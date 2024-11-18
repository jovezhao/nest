package com.zhaofujun.nest.utils;

import java.util.function.Supplier;
import com.zhaofujun.nest.provider.LockProvider;
import com.zhaofujun.nest.NestConst;
import com.zhaofujun.nest.manager.ProviderManager;

public class LockUtil {
    public static <T> T lock(String name, Supplier<T> runnable) {
        return lock(name, "default", runnable);
    }

    public static <T> T lock(String name, String provider, Supplier<T> supplier) {
        var lockProvider = ProviderManager.get(LockProvider.class, provider, NestConst.defaultLockProvider);
        return lockProvider.lock(name, () -> supplier.get());
    }
}
