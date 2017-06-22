package com.jovezhao.nest.rabbitmq;

import com.jovezhao.nest.ddd.event.provider.distribut.DistributedEventProducer;
import com.jovezhao.nest.ddd.event.provider.distribut.EventData;
import com.jovezhao.nest.utils.JsonUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import org.springframework.util.SerializationUtils;

/**
 * Created by zhaofujun on 2017/6/22.
 */
public class RabbitMQEventProducer extends DistributedEventProducer<RabbitMQProviderConfig> {


    @Override
    public void commitMessage(String eventName, EventData eventData) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setAutomaticRecoveryEnabled(true);
        factory.setHost(this.getProviderConfig().getHost());
        factory.setPort(this.getProviderConfig().getPort());
        factory.setUsername(this.getProviderConfig().getUser());
        factory.setPassword(this.getProviderConfig().getPwd());
        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();
        channel.exchangeDeclare(eventName, "fanout", true, false, null);

        channel.basicPublish(eventName, "", MessageProperties.PERSISTENT_TEXT_PLAIN, JsonUtils.toJsonString(eventData).getBytes());
        channel.close();
        connection.close();


    }

}
