package com.zhaofujun.nest.lock;

import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.configuration.LockConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DefaultLockProvider implements LockProvider {

    Map<String, Lock> lockMap = new HashMap<>();
    public final static String CODE = "DefaultLockProvider";

    @Override
    public String lock(String key) {
        String requestId = UUID.randomUUID().toString();
        LockConfiguration lockConfiguration = NestApplication.current().getLockConfiguration();
        Lock lock = getLock(key);
        try {
            if (lock.tryLock(lockConfiguration.getWaitTime(), TimeUnit.MILLISECONDS))
                return requestId;
            else
                return "";
        } catch (InterruptedException e) {
            return "";
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
    public void unlock(String key,String requestId) {
        Lock lock = getLock(key);
        lock.unlock();
    }

    @Override
    public String getCode() {
        return CODE;
    }
}
