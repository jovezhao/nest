package com.zhaofujun.nest.inner;

import java.util.HashMap;
import java.util.Map;

import com.zhaofujun.nest.NestConst;
import com.zhaofujun.nest.utils.lock.LockProvider;

/**
 * 默认的本地锁实现方式
 * 该方式会为每一个锁维护创建一个静态对象，并且使用该静态对象进行排他处理方式
 */
public class DefaultLockProvider implements LockProvider {

    private static Map<String, Object> lockMap = new HashMap<>();

    /***
     * 当存在对象的时候， 直接返回对象，不存在的时候创建一个新对象并返回
     * 
     * @param name
     * @return
     */
    private Object getLockObject(String name) {
        Object lockObject = lockMap.get(name);
        if (lockObject == null) {
            lockObject = new Object();
            lockMap.put(name, lockObject);
        }
        return lockObject;

    }

    @Override
    public String getCode() {
        return NestConst.defaultLockProvider;
    }

    @Override
    public void lock(String name, Runnable runnable) {
        Object lockObject = getLockObject(name);
        synchronized (lockObject) {
            runnable.run();
        }
    }

}
