package com.ywkj.nest.activemq;

import com.ywkj.nest.core.utils.JsonUtils;

import javax.jms.*;
import java.io.Serializable;

/**
 * Created by Jove on 2016/9/23.
 */
class ActiveMQProducer {

    ConnectionFactory connectionFactory;

    public ActiveMQProducer(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void publish(String eventName, Serializable object) throws JMSException {
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination topic = session.createTopic("VirtualTopic." + eventName);
        MessageProducer producer = session.createProducer(topic);
        producer.send(session.createTextMessage(JsonUtils.toJsonString(object)));
        producer.close();
        session.close();
        connection.close();
    }


}
