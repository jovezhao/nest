package com.ywkj.nest.activemq;

import com.ywkj.nest.core.utils.JsonUtils;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import java.io.Serializable;

/**
 * Created by Jove on 2016/9/23.
 */
class ActiveMQProducer {

    Connection connection;

    public ActiveMQProducer(Connection connection) {
        this.connection = connection;
    }

    public void publish(String eventName, Serializable object) throws JMSException {
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = session.createProducer(session.createTopic(eventName));
        producer.send(session.createTextMessage(JsonUtils.toJsonString(object)));
        producer.close();
        session.close();

    }


}
