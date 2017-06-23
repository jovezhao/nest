package com.jovezhao.nest.activemq;

import com.jovezhao.nest.ddd.event.provider.distribut.DistributedEventConsumer;
import com.jovezhao.nest.ddd.event.provider.distribut.EventData;
import com.jovezhao.nest.ddd.event.provider.distribut.EventDataProcessor;
import com.jovezhao.nest.utils.JsonUtils;
import org.apache.activemq.pool.PooledConnectionFactory;

import javax.jms.*;

/**
 * Created by zhaofujun on 2017/6/22.
 */
public class ActiveMQEventConsumer extends DistributedEventConsumer<ActiveMQProviderConfig> {
    Connection connection;

    @Override
    protected void init() throws Exception {
        ConnectionFactory connectionFactory = new PooledConnectionFactory(this.getProviderConfig().getBrokers());
        connection = connectionFactory.createConnection();
        connection.start();
    }

    @Override
    protected void consume(EventDataProcessor processor) throws Exception {
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        Destination queue = session.createQueue("Consumer." + this.getEventHandler().getHandlerName() + ".VirtualTopic." + this.getEventHandler().getEventName());
        MessageConsumer consumer = session.createConsumer(queue);
        TextMessage textMessage = (TextMessage) consumer.receive();

        processor.setEventData(textMessage.getText());

        try {
            processor.process();
            textMessage.acknowledge();
        } finally {
            consumer.close();
            session.close();
        }


    }

    @Override
    protected void dispose() throws Exception {
        connection.close();
    }
}
