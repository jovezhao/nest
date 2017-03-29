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
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = session.createProducer(session.createTopic("VirtualTopic."+eventName));
        producer.send(session.createTextMessage(JsonUtils.toJsonString(object)));
        producer.close();
        session.close();

    }


}
