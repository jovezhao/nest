package com.ywkj.nest.activemq;


import com.ywkj.nest.core.exception.SystemException;
import com.ywkj.nest.core.log.ILog;
import com.ywkj.nest.core.log.LogAdapter;
import com.ywkj.nest.core.utils.JsonUtils;

import javax.jms.*;

/**
 * Created by Jove on 2016/9/23.
 */
public class ActiveMQConsumer implements Runnable {


    private ILog logger = new LogAdapter(ActiveMQConsumer.class);
    private ConnectionFactory connectionFactory;
    private EventWork work;
    private int prefetchCount;
    private volatile boolean status;

    public ActiveMQConsumer(ConnectionFactory connectionFactory, EventWork work, int prefetchCount) {
        this.work = work;
        this.prefetchCount = prefetchCount;
        this.connectionFactory = connectionFactory;
    }

    public void stop() {

        this.status = false;
        destroy();

    }

    Connection connection = null;
    Session session = null;
    MessageConsumer consumer = null;

    private void destroy() {
        if (consumer != null) {
            try {
                consumer.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
        if (session != null) {
            try {
                session.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        status = true;

        try {
            connection = connectionFactory.createConnection();//Consumer.A.VirtualTopic.TEST
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            consumer = session.createConsumer(session.createQueue("Consumer." + work.getHandlerName() + "VirtualTopic." + work.getEventName()));
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        EventDataDto dto = JsonUtils.toObj(textMessage.getText(), EventDataDto.class);
                        boolean flag = work.doWork(dto);
                        if (flag)
                            message.acknowledge();

                    } catch (JMSException e) {
                        e.printStackTrace();
                        throw new SystemException(e);
                    }
                }
            });

        } catch (JMSException ex) {
            destroy();
            if (status) {
                run();
            }
        }

    }

}
