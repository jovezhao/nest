package com.zhaofujun.nest3.application.config;


import com.zhaofujun.nest3.application.config.ConfigurationItem;

/**
 * 缓存组配置
 *
 * @author Jove
 */
public class CacheConfiguration extends ConfigurationItem {
    /**
     * 名称
     */
    private String name;
    /**
     * 策略提供者
     */
    private String cacheProviderCode;
    /**
     * 过期时间
     */
    private long idleSeconds;

    private String jsonProviderCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getCacheProviderCode() {
        return cacheProviderCode;
    }

    public void setCacheProviderCode(String cacheProviderCode) {
        this.cacheProviderCode = cacheProviderCode;
    }

    public long getIdleSeconds() {
        return idleSeconds;
    }

    public String getJsonProviderCode() {
        return jsonProviderCode;
    }

    public void setJsonProviderCode(String jsonProviderCode) {
        this.jsonProviderCode = jsonProviderCode;
    }

    public void setIdleSeconds(long idleSeconds) {
        this.idleSeconds = idleSeconds;
    }

}
