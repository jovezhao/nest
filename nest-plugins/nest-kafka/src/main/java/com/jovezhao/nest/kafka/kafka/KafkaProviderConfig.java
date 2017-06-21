package com.jovezhao.nest.kafka.kafka;

import com.jovezhao.nest.ddd.event.ProviderConfig;

/**
 * kafka配置文件
 * Created by zhaofujun on 2017/6/22.
 */
public class KafkaProviderConfig extends ProviderConfig {
    private String zkconnect;
    private String brokers;

    public String getZkconnect() {
        return zkconnect;
    }

    public void setZkconnect(String zkconnect) {
        this.zkconnect = zkconnect;
    }

    public String getBrokers() {
        return brokers;
    }

    public void setBrokers(String brokers) {
        this.brokers = brokers;
    }
}
