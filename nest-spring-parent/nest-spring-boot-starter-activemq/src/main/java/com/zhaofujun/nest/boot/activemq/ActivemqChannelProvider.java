package com.zhaofujun.nest.boot.activemq;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import com.zhaofujun.nest.ddd.event.EventChannelProvider;
import com.zhaofujun.nest.ddd.event.EventData;
import com.zhaofujun.nest.ddd.event.PullChannelConsumer;
import com.zhaofujun.nest.ddd.event.PushChannelConsumer;
import com.zhaofujun.nest.utils.JsonUtil;

import jakarta.jms.Topic;

// import javax.jms.JMSException;
// import javax.jms.Message;
// import javax.jms.Session;
// import javax.jms.Topic;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Session;

@Component
public class ActivemqChannelProvider implements EventChannelProvider {

    @Autowired
    private JmsTemplate jmsTemplate;
   

    @Override
    public String getCode() {
        return "ActivemqChannelProvider";
    }

    @Override
    public void commit(String messageGroup, EventData<?> dataObject) {
        Topic topic = new ActiveMQTopic("VirtualTopic." + messageGroup);

        String messageText = JsonUtil.toJsonString(dataObject);
        jmsTemplate.send(topic, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(messageText);
            }
        });
    }

    @Override
    public PullChannelConsumer getPullChannelConsumer() {
        return new ActivemqPullConsumer(jmsTemplate);
    }

    @Override
    public PushChannelConsumer getPushChannelConsumer() {
        return new ActivemqPushConsumer(jmsTemplate.getConnectionFactory());
    }

}
