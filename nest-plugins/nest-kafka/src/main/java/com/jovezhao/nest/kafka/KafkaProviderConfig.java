package com.jovezhao.nest.kafka;

import com.jovezhao.nest.ddd.event.ProviderConfig;

/**
 * kafka配置文件
 * Created by zhaofujun on 2017/6/22.
 */
public class KafkaProviderConfig extends ProviderConfig {
    private String zk;
    private String brokers;

    public String getZk() {
        return zk;
    }

    public void setZk(String zk) {
        this.zk = zk;
    }

    public String getBrokers() {
        return brokers;
    }

    public void setBrokers(String brokers) {
        this.brokers = brokers;
    }
}
