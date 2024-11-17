package com.zhaofujun.nest.utils.lock;

/**
 * 锁配置项
 */
public class LockItem {
    /**
     * 锁名称
     */
    private String name;
    /**
     * 锁提供者代号
     */
    private String provider;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

}
