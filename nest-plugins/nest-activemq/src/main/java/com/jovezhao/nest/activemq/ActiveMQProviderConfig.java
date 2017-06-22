package com.jovezhao.nest.activemq;

import com.jovezhao.nest.ddd.event.ProviderConfig;

/**
 * kafka配置文件
 * Created by zhaofujun on 2017/6/22.
 */
public class ActiveMQProviderConfig extends ProviderConfig {
    private String brokers;


    public String getBrokers() {
        return brokers;
    }

    public void setBrokers(String brokers) {
        this.brokers = brokers;
    }
}
