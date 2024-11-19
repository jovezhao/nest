package com.zhaofujun.nest.utils;

import java.util.function.Supplier;
import com.zhaofujun.nest.provider.LockProvider;
import com.zhaofujun.nest.NestConst;
import com.zhaofujun.nest.manager.ProviderManager;

/**
 * 锁工具类。
 * 
 * @author zhaofujun
 */
public class LockUtil {

    /**
     * 执行锁操作。
     * 
     * @param name    锁名称
     * @param runnable 锁内执行的代码
     * @return 锁内执行的代码返回值
     */
    public static <T> T lock(String name, Supplier<T> runnable) {
        return lock(name, NestConst.defaultLockProvider, runnable);
    }

    /**
     * 执行锁操作。
     * 
     * @param name     锁名称
     * @param provider 锁提供者名称
     * @param supplier 锁内执行的代码
     * @return 锁内执行的代码返回值
     */
    public static <T> T lock(String name, String provider, Supplier<T> supplier) {
        var lockProvider = ProviderManager.get(LockProvider.class, provider, NestConst.defaultLockProvider);
        return lockProvider.lock(name, () -> supplier.get());
    }
}
