package com.zhaofujun.nest.utils.cache;

/**
 * CacheItem 类
 * 用于表示缓存项的基本信息，包括缓存项的代码、缓存提供者的代码、最大存活时间以及是否禁用。
 */
public class CacheItem {
    /**
     * 缓存项的唯一标识代码
     */
    private String code;

    /**
     * 缓存提供者的代码
     */
    private String cacheProviderCode;

    /**
     * 缓存项的最大存活时间（秒）
     */
    private long maxLiveSeconds;

    /**
     * 缓存项是否被禁用
     */
    private boolean disabled;

    /**
     * 获取缓存项的唯一标识代码
     *
     * @return 缓存项的唯一标识代码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置缓存项的唯一标识代码
     *
     * @param code 缓存项的唯一标识代码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取缓存提供者的代码
     *
     * @return 缓存提供者的代码
     */
    public String getCacheProviderCode() {
        return cacheProviderCode;
    }

    /**
     * 设置缓存提供者的代码
     *
     * @param cacheProviderCode 缓存提供者的代码
     */
    public void setCacheProviderCode(String cacheProviderCode) {
        this.cacheProviderCode = cacheProviderCode;
    }

    /**
     * 获取缓存项的最大存活时间（秒）
     *
     * @return 缓存项的最大存活时间（秒）
     */
    public long getMaxLiveSeconds() {
        return maxLiveSeconds;
    }

    /**
     * 设置缓存项的最大存活时间（秒）
     *
     * @param maxLiveSeconds 缓存项的最大存活时间（秒）
     */
    public void setMaxLiveSeconds(long maxLiveSeconds) {
        this.maxLiveSeconds = maxLiveSeconds;
    }

    /**
     * 获取缓存项是否被禁用
     *
     * @return 缓存项是否被禁用
     */
    public boolean isDisabled() {
        return disabled;
    }

    /**
     * 设置缓存项是否被禁用
     *
     * @param disabled 缓存项是否被禁用
     */
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}