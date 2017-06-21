package com.jovezhao.nest.ddd.event;

import java.util.Properties;

/**
 * 通道配置
 * Created by zhaofujun on 2017/6/21.
 */
public class ProviderConfig {
    private Properties properties;

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
