package com.jovezhao.nest.activemq;


import com.jovezhao.nest.core.exception.SystemException;
import com.jovezhao.nest.core.log.ILog;
import com.jovezhao.nest.core.log.LogAdapter;
import com.jovezhao.nest.core.utils.JsonUtils;

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

    private void doWork() {
        try {
            connection = connectionFactory.createConnection();//Consumer.A.VirtualTopic.TEST
            if (connection != null)
                connection.start();
            session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            Destination queue = session.createQueue("Consumer." + work.getHandlerName() + ".VirtualTopic." + work.getEventName());
            consumer = session.createConsumer(queue);
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
                //发生错误时等待1秒再重新尝试
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                doWork();
            }
        } catch (Exception ex) {
            //其它异常发生，需要外部检查
            ex.printStackTrace();
            logger.fatal(ex);
        }
    }

    @Override
    public void run() {
        System.out.println("run now");

        this.status = true;
        doWork();


    }

}
