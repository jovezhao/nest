package com.zhaofujun.nest.springboot.activemq;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import com.zhaofujun.nest.ddd.event.EventData;
import com.zhaofujun.nest.ddd.event.MessageAck;
import com.zhaofujun.nest.ddd.event.MessageProcessor;
import com.zhaofujun.nest.ddd.event.PushChannelConsumer;
import com.zhaofujun.nest.utils.JsonUtil;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.Queue;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

public class ActivemqPushConsumer extends PushChannelConsumer {

    private ConnectionFactory connectionFactory;

    public ActivemqPushConsumer(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    protected void subscribe(MessageProcessor<? super EventData> consumer) {
        Queue queue = new ActiveMQQueue(
                "Consumer." + consumer.getHandlerId() + ".VirtualTopic." + consumer.getEventName());

        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setDestination(queue);
        container.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        container.setMessageListener(new MessageListener() {

            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    EventData<?> eventData = JsonUtil.parseObject(textMessage.getText(), EventData.class);
                    MessageAck messageAck = consumer.invoke(eventData);
                    if (messageAck.equals(MessageAck.SUCCESS)) {
                        message.acknowledge();
                    }
                } catch (JMSException ex) {

                }
            }
        });
        container.start();
    }

}