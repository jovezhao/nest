package com.zhaofujun.nest.utils.cache;

public class CacheItem {
    private String code;
    private String cacheProviderCode;
    private long idleSeconds;
    private boolean disabled;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public void setIdleSeconds(long idleSeconds) {
        this.idleSeconds = idleSeconds;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

}
