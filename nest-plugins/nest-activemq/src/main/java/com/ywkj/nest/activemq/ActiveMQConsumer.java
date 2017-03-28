package com.ywkj.nest.activemq;


import com.ywkj.nest.core.log.ILog;
import com.ywkj.nest.core.log.LogAdapter;
import com.ywkj.nest.core.utils.JsonUtils;

import javax.jms.*;

/**
 * Created by Jove on 2016/9/23.
 */
public class ActiveMQConsumer implements Runnable {


    public ActiveMQConsumer(String zkconnect, EventWork work, int prefetchCount) {
        this.zkconnect = zkconnect;
        this.work = work;
        this.prefetchCount = prefetchCount;
    }

    private ILog logger = new LogAdapter(ActiveMQConsumer.class);
    private Connection connection;
    private String zkconnect;
    private EventWork work;
    private int prefetchCount;
    private volatile boolean status;

    public ActiveMQConsumer(Connection connection, int prefetchCount) throws JMSException {
        this.connection = connection;
    }

    public void stop() {
        this.status = false;
    }


    @Override
    public void run() {
        status = true;

        try {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageConsumer consumer = session.createConsumer(session.createQueue(work.getEventName()));
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        EventDataDto dto = JsonUtils.toObj(textMessage.getText(), EventDataDto.class);
                        boolean flag = work.doWork(dto);
                        if(flag)
                            message.acknowledge();

                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

}
