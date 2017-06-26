package com.jovezhao.nest.activemq;

import com.jovezhao.nest.ddd.event.provider.distribut.DistributedEventConsumer;
import com.jovezhao.nest.ddd.event.provider.distribut.MessageProcessor;
import com.jovezhao.nest.exception.SystemException;
import com.jovezhao.nest.log.Log;
import com.jovezhao.nest.log.LogAdapter;
import org.apache.activemq.pool.PooledConnectionFactory;

import javax.jms.*;

/**
 * Created by zhaofujun on 2017/6/22.
 */
public class ActiveMQEventConsumer extends DistributedEventConsumer<ActiveMQProviderConfig> {
    private Log log = new LogAdapter(this.getClass());
    private ConnectionFactory connectionFactory;

    public ActiveMQEventConsumer(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }



    @Override
    protected void consume(MessageProcessor processor) throws Exception {
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        Destination queue = session.createQueue("Consumer." + this.getEventHandler().getHandlerName() + ".VirtualTopic." + this.getEventHandler().getEventName());
        MessageConsumer consumer = session.createConsumer(queue);
        TextMessage textMessage = (TextMessage) consumer.receive();

        processor.setMessageData(textMessage.getText());

        try {
            processor.process();
            textMessage.acknowledge();
        } catch (Exception ex) {
            //处理handler时发生异常，记录警告类日志
            log.warn(ex);
        } finally {
            consumer.close();
            session.close();
            connection.close();
        }


    }


}
