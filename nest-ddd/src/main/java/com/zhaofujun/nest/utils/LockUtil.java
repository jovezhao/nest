package com.zhaofujun.nest.utils;

public class LockUtil {
    public void lock(String name, Runnable runnable) {
        lock(name, "default", runnable);
    }

    public void lock(String name, String provider, Runnable runnable) {

    }
}
