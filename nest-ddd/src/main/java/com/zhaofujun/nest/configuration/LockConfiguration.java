package com.zhaofujun.nest.configuration;

import com.zhaofujun.nest.lock.DefaultLockProvider;

public class LockConfiguration {
    private String provider= DefaultLockProvider.CODE;
    private int retry=3;
    private int timeout=5000;
    private int waitTime=1000;

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public int getRetry() {
        return retry;
    }

    public void setRetry(int retry) {
        this.retry = retry;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }
}
