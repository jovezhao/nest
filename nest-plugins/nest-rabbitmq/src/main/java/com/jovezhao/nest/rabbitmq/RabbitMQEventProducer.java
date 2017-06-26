package com.jovezhao.nest.rabbitmq;

import com.jovezhao.nest.ddd.event.provider.distribut.DistributedEventProducer;
import com.jovezhao.nest.ddd.event.provider.distribut.MessageData;
import com.jovezhao.nest.exception.SystemException;
import com.jovezhao.nest.utils.JsonUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * Created by zhaofujun on 2017/6/22.
 */
public class RabbitMQEventProducer extends DistributedEventProducer<RabbitMQProviderConfig> {

    private ConnectionFactory connectionFactory;

    public RabbitMQEventProducer(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void commitMessage(String eventName, MessageData messageData) {
        try {

            Connection connection = connectionFactory.newConnection();

            Channel channel = connection.createChannel();
            channel.exchangeDeclare(eventName, "fanout", true, false, null);

            channel.basicPublish(eventName, "", MessageProperties.PERSISTENT_TEXT_PLAIN, JsonUtils.toJsonString(messageData).getBytes());
            channel.close();
            connection.close();
        } catch (Exception ex) {
            throw new SystemException("发送RabbitMQ消息催告", ex);
        }

    }

}
