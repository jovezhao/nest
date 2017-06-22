package com.jovezhao.nest.rabbitmq;

import com.jovezhao.nest.ddd.event.ProviderConfig;

/**
 * Created by zhaofujun on 2017/6/22.
 */
public class RabbitMQProviderConfig extends ProviderConfig {
    private String host;
    private int port;
    private String user;
    private String pwd;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
