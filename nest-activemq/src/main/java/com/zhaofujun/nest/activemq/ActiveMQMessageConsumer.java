package com.zhaofujun.nest.activemq;

import com.zhaofujun.nest.container.BeanFinder;
import com.zhaofujun.nest.context.event.EventHandler;
import com.zhaofujun.nest.context.event.message.MessageInfo;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageConsumer;
import com.zhaofujun.nest.utils.JsonUtils;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ActiveMQMessageConsumer extends DistributeMessageConsumer {

    private String brokers;

    public ActiveMQMessageConsumer(BeanFinder beanFinder, String brokers) {
        super(beanFinder);
        this.brokers = brokers;
    }

    @Override
    public void subscribe(EventHandler eventHandler) {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokers);
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            Queue queue = session.createQueue("Consumer." + eventHandler.getClass().getSimpleName() + ".VirtualTopic." + eventHandler.getEventCode());
            MessageConsumer consumer = session.createConsumer(queue);
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    TextMessage textMessage = (TextMessage) message;
                    String messageText = null;
                    try {
                        messageText = textMessage.getText();
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                    MessageInfo messageInfo = JsonUtils.toObj(messageText, MessageInfo.class);

                    ConsumerContext consumerContext = new ConsumerContext(connection, session, consumer, message);

                    onReceivedMessage(messageInfo, eventHandler, consumerContext);

                }
            });
        } catch (Exception ex) {

        }
    }

    @Override
    protected void onFailed(EventHandler eventHandler, Object context, Exception ex) {

    }

    @Override
    protected void onEnds(EventHandler eventHandler, Object context) {
        ConsumerContext consumerContext = (ConsumerContext) context;
        try {
            consumerContext.getMessage().acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }


}
