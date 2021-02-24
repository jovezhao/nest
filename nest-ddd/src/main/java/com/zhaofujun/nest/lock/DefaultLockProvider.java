package com.zhaofujun.nest.lock;

import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.configuration.LockConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DefaultLockProvider implements LockProvider {

    Map<String, Lock> lockMap = new HashMap<>();
    public final static String CODE = "DefaultLockProvider";

    @Override
    public boolean lock(String key) {
        LockConfiguration lockConfiguration = NestApplication.current().getLockConfiguration();
        Lock lock = getLock(key);
        try {
            return lock.tryLock(lockConfiguration.getTimeout(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            return false;
        }
    }

    private synchronized Lock getLock(String key) {
        Lock lock = lockMap.get(key);
        if (lock == null) {
            lock = new ReentrantLock();
            lockMap.put(key, lock);
        }
        return lock;
    }

    @Override
    public void unlock(String key) {
        Lock lock = getLock(key);
        lock.unlock();
    }

    @Override
    public String getCode() {
        return CODE;
    }
}
