package com.zhaofujun.nest.springboot.activemq;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.jms.core.JmsTemplate;

import com.zhaofujun.nest.ddd.event.EventData;
import com.zhaofujun.nest.ddd.event.MessageAck;
import com.zhaofujun.nest.ddd.event.MessageProcessor;
import com.zhaofujun.nest.ddd.event.PullChannelConsumer;
import com.zhaofujun.nest.utils.JsonUtil;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Queue;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

public class ActivemqPullConsumer extends PullChannelConsumer {

    private JmsTemplate jmsTemplate;

    public ActivemqPullConsumer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public boolean pull(MessageProcessor eventConsumer) {
        Queue queue = new ActiveMQQueue(
                "Consumer." + eventConsumer.getHandlerId() + ".VirtualTopic." + eventConsumer.getEventName());
        jmsTemplate.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        try {
            Message message = jmsTemplate.receive(queue);
            TextMessage textMessage = (TextMessage) message;
            EventData<?> eventData = JsonUtil.parseObject(textMessage.getText(), EventData.class);
            MessageAck messageAck = eventConsumer.invoke(eventData);
            if (messageAck.equals(MessageAck.SUCCESS)) {
                message.acknowledge();
                return true;
            }
            return false;
        } catch (JMSException ex) {
            return false;
        }
    }

}