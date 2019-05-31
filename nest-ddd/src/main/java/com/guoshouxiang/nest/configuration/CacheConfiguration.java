package com.guoshouxiang.nest.configuration;


/**
 * 缓存组配置
 *
 * @author Jove
 */
public class  CacheConfiguration {
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 策略提供者
     */
    private String providerCode;
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


    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    public long getIdleSeconds() {
        return idleSeconds;
    }

    public void setIdleSeconds(long idleSeconds) {
        this.idleSeconds = idleSeconds;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
