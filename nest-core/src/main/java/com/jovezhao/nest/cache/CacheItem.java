package com.jovezhao.nest.cache;


/**
 * 缓存组配置
 *
 * @author Jove
 */
public class CacheItem {
    private String coce;
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

    public String getCoce() {
        return coce;
    }

    public void setCoce(String coce) {
        this.coce = coce;
    }
}
