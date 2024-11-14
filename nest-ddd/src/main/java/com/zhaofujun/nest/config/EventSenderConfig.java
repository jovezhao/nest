package com.zhaofujun.nest.config;

public class EventSenderConfig {
    private int commonSize=20;
    private int failSize=10;
    private int maxFailTime=5;
    private int sleepTime=1000;
    public int getCommonSize() {
        return commonSize;
    }
    public void setCommonSize(int commonSize) {
        this.commonSize = commonSize;
    }
    public int getFailSize() {
        return failSize;
    }
    public void setFailSize(int failSize) {
        this.failSize = failSize;
    }
    public int getMaxFailTime() {
        return maxFailTime;
    }
    public void setMaxFailTime(int maxFailTime) {
        this.maxFailTime = maxFailTime;
    }
    public int getSleepTime() {
        return sleepTime;
    }
    public void setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
    }
    
}