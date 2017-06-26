package com.jovezhao.nest.rabbitmq;

import com.jovezhao.nest.ddd.event.ChannelProvider;
import com.jovezhao.nest.ddd.event.EventConsumer;
import com.jovezhao.nest.ddd.event.EventProducer;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by zhaofujun on 2017/6/22.
 */
public class RabbitMQChannelProvider extends ChannelProvider<RabbitMQProviderConfig> {
    ConnectionFactory connectionFactory;
    @Override
    public void init() {
        connectionFactory = new ConnectionFactory();
        connectionFactory.setAutomaticRecoveryEnabled(true);
        connectionFactory.setHost(this.getProviderConfig().getHost());
        connectionFactory.setPort(this.getProviderConfig().getPort());
        connectionFactory.setUsername(this.getProviderConfig().getUser());
        connectionFactory.setPassword(this.getProviderConfig().getPwd());
    }

    @Override
    protected EventConsumer createEventConsumer() {
        return new RebbitMQEventConsumer(connectionFactory);
    }

    @Override
    public EventProducer createEventProducer() {
        return new RabbitMQEventProducer(connectionFactory);
    }
}
