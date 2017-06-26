package com.jovezhao.nest.activemq;

import com.jovezhao.nest.ddd.event.provider.distribut.DistributedEventProducer;
import com.jovezhao.nest.ddd.event.provider.distribut.MessageData;
import com.jovezhao.nest.exception.SystemException;
import com.jovezhao.nest.utils.JsonUtils;
import org.apache.activemq.pool.PooledConnectionFactory;

import javax.jms.*;

/**
 * Created by zhaofujun on 2017/6/22.
 */
public class ActiveMQEventProducer extends DistributedEventProducer<ActiveMQProviderConfig> {
    private ConnectionFactory connectionFactory;

    public ActiveMQEventProducer(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    protected void commitMessage(String eventName, MessageData messageData) {

        try {
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination topic = session.createTopic("VirtualTopic." + eventName);
            MessageProducer producer = session.createProducer(topic);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            producer.send(session.createTextMessage(JsonUtils.toJsonString(messageData)));
            producer.close();
            session.close();
            connection.close();
        } catch (Exception ex) {
            throw new SystemException("发送ActiveMQ消息失败", ex);
        }
    }
}
