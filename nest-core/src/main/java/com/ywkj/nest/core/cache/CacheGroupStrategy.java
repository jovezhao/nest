package com.ywkj.nest.core.cache;


/**
 * 缓存组策略
 *
 * @author Jove
 */
public class CacheGroupStrategy {
    /**
     * 名称
     */
    private String name;
    /**
     * 策略提供者
     */
    private ICacheProvider provider;
    /**
     * 过期时间
     */
    private long idleSeconds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ICacheProvider getProvider() {
        return provider;
    }

    public void setProvider(ICacheProvider provider) {
        this.provider = provider;
    }

    public long getIdleSeconds() {
        return idleSeconds;
    }

    public void setIdleSeconds(long idleSeconds) {
        this.idleSeconds = idleSeconds;
    }
}
