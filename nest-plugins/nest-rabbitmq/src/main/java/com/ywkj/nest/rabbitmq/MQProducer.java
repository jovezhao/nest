package com.ywkj.nest.rabbitmq;


import com.ywkj.nest.core.log.ILog;
import com.ywkj.nest.core.log.LogAdapter;

import java.io.Serializable;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import org.springframework.util.SerializationUtils;

/**
 * Created by Jove on 2016-03-22.
 */
class MQProducer {
    ILog logger = new LogAdapter(MQProducer.class);
    private MQConnection connection;

    public MQProducer(MQConnection connection) {
        this.connection = connection;

    }

    public void publish(String eventName, Serializable object) {
        Channel channel = null;
        try {
            long now = System.currentTimeMillis();
            channel = this.connection.getConnection().createChannel();
            channel.exchangeDeclare(eventName, "fanout", true, false, null);
            logger.info("创建exchange" + (now = System.currentTimeMillis() - now));
            channel.basicPublish(eventName, "", MessageProperties.PERSISTENT_TEXT_PLAIN, SerializationUtils.serialize(object));
            logger.info("publish" + (System.currentTimeMillis() - now));
            logger.info("事件发布完成", object);
        } catch (Exception e) {
            logger.warn(e.getMessage());
        } finally {
            if (channel != null && channel.isOpen()) {
                try {
                    channel.close();
                } catch (Exception e) {
                    logger.warn(e.getMessage());
                }
            }
        }
    }
}

