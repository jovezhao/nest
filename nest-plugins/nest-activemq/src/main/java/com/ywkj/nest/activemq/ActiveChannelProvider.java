package com.ywkj.nest.activemq;

import com.ywkj.nest.core.exception.SystemException;
import com.ywkj.nest.ddd.event.AbstractChannelProvider;
import com.ywkj.nest.ddd.event.IEventHandler;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

/**
 * Created by Jove on 2016/7/27.
 */

public class ActiveChannelProvider extends AbstractChannelProvider {


    private int prefetchCount;
    private volatile boolean status;
    PooledConnectionFactory connectionFactory;
    private ActiveMQProducer producer;
    private ActiveMQConsumer consumer;

    public ActiveChannelProvider(String brokers, int prefetchCount) {
        connectionFactory = new PooledConnectionFactory(brokers);
        producer = new ActiveMQProducer(connectionFactory);
        this.prefetchCount = prefetchCount;
    }

    @Override
    public void publish(String eventName, Object data) {
        EventDataDto dto = new EventDataDto(data);
        try {
            producer.publish(eventName, dto);
        } catch (JMSException e) {
            e.printStackTrace();
            throw new SystemException(e);
        }

    }


    @Override
    public void subscribe(String eventName, IEventHandler handler) {
        consumer = new ActiveMQConsumer(connectionFactory, new EventWork(eventName, handler), prefetchCount);
        status = true;
        Thread workThread = new Thread(consumer);
        workThread.start();

    }

    @Override
    public void stop() {
        connectionFactory.stop();
        if (consumer != null)
            consumer.stop();
    }
}
