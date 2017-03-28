package com.ywkj.nest.activemq;

import com.ywkj.nest.ddd.event.AbstractChannelProvider;
import com.ywkj.nest.ddd.event.IEventHandler;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

/**
 * Created by Jove on 2016/7/27.
 */

public class ActiveChannelProvider extends AbstractChannelProvider {


    private int prefetchCount;
    private volatile boolean status;

    private ActiveMQProducer producer;
    private ActiveMQConsumer consumer;

    public ActiveChannelProvider(String brokers, int prefetchCount) throws JMSException {
        ConnectionFactory connectionFactory=new ActiveMQConnectionFactory(brokers);
        Connection connection = connectionFactory.createConnection();
        producer=new ActiveMQProducer(connection);
        consumer=new ActiveMQConsumer(connection,prefetchCount);
    }

    @Override
    public void publish(String eventName, Object data) {
        EventDataDto dto = new EventDataDto(data);
        producer.publish(eventName, dto);

    }



    @Override
    public void subscribe(String eventName, IEventHandler handler) {


        status = true;
        Thread workThread = new Thread(consumer);
        workThread.start();

    }

    @Override
    public void stop() {
        if (consumer != null)
            consumer.stop();
    }
}
