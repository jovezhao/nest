package com.jovezhao.nest.activemq;

import com.jovezhao.nest.ddd.event.ChannelProvider;
import com.jovezhao.nest.ddd.event.EventConsumer;
import com.jovezhao.nest.ddd.event.EventProducer;
import org.apache.activemq.pool.PooledConnectionFactory;

import javax.jms.ConnectionFactory;

/**
 * Created by Jove on 2016/7/27.
 */

public class ActiveMQChannelProvider extends ChannelProvider<ActiveMQProviderConfig> {

    private ConnectionFactory connectionFactory;

    @Override
    public void init() {
        connectionFactory = new PooledConnectionFactory(this.getProviderConfig().getBrokers());
    }

    @Override
    protected EventConsumer createEventConsumer() {
        return new ActiveMQEventConsumer(connectionFactory);
    }

    @Override
    public EventProducer createEventProducer() {
        return new ActiveMQEventProducer(connectionFactory);
    }
}
