package com.zhaofujun.nest.activemq;

import com.zhaofujun.nest.context.event.message.MessageInfo;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageProducer;
import com.zhaofujun.nest.utils.JsonUtils;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ActiveMQMessageProducer extends DistributeMessageProducer {
    private String brokers;

    public ActiveMQMessageProducer(String brokers) {
        this.brokers = brokers;
    }

    @Override
    public void commit(String messageGroup, MessageInfo messageInfo) {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokers);
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic("VirtualTopic."+messageGroup);
            MessageProducer producer = session.createProducer(topic);
            producer.send(session.createTextMessage(JsonUtils.toJsonString(messageInfo)));
            producer.close();
            session.close();
            connection.close();
        } catch (Exception ex) {

        }
    }

    @Override
    public void destruction() {

    }
}
