package com.jovezhao.nest.activemq;

import com.jovezhao.nest.ddd.event.provider.distribut.DistributedEventProducer;
import com.jovezhao.nest.ddd.event.provider.distribut.EventData;
import com.jovezhao.nest.utils.JsonUtils;
import org.apache.activemq.pool.PooledConnectionFactory;

import javax.jms.*;

/**
 * Created by zhaofujun on 2017/6/22.
 */
public class ActiveMQEventProducer extends DistributedEventProducer<ActiveMQProviderConfig> {
    @Override
    protected void commitMessage(String eventName, EventData eventData) throws Exception {

        ConnectionFactory connectionFactory = new PooledConnectionFactory(this.getProviderConfig().getBrokers());
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination topic = session.createTopic("VirtualTopic." + eventName);
        MessageProducer producer = session.createProducer(topic);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        producer.send(session.createTextMessage(JsonUtils.toJsonString(eventData)));
        producer.close();
        session.close();
        connection.close();
    }
}
