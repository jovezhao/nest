package com.jovezhao.nest.rabbitmq;

import com.jovezhao.nest.ddd.event.provider.distribut.DistributedEventConsumer;
import com.jovezhao.nest.ddd.event.provider.distribut.EventData;
import com.jovezhao.nest.ddd.event.provider.distribut.EventDataProcessor;
import com.jovezhao.nest.utils.JsonUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

/**
 * Created by zhaofujun on 2017/6/22.
 */
public class RebbitMQEventConsumer extends DistributedEventConsumer<RabbitMQProviderConfig> {
    Connection connection;

    @Override
    protected void init() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setAutomaticRecoveryEnabled(true);
        factory.setHost(this.getProviderConfig().getHost());
        factory.setPort(this.getProviderConfig().getPort());
        factory.setUsername(this.getProviderConfig().getUser());
        factory.setPassword(this.getProviderConfig().getPwd());
        connection = factory.newConnection();
    }

    @Override
    protected void consume() throws Exception {
        int prefetchCount = 5;
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(this.getEventHandler().getEventName(), "fanout", true, false, null);
        channel.basicQos(prefetchCount);
        //申明消息队列
        channel.queueDeclare(this.getEventHandler().getHandlerName(), true, false, false, null);
        channel.queueBind(this.getEventHandler().getHandlerName(), this.getEventHandler().getEventName(), "");

        QueueingConsumer consumer = new QueueingConsumer(channel);
        // 第二个参数必须为false，设置必须要ack相应
        channel.basicConsume(this.getEventHandler().getHandlerName(), false, consumer);
        //设置每次都把当前要取的数据取完再关闭当前channel
        for (int i = 0; i < prefetchCount; i++) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();


            String json = new String(delivery.getBody());
            EventData eventData = JsonUtils.toObj(json, EventData.class);
            EventDataProcessor processor = new EventDataProcessor(eventData, this.getEventHandler());
            try {
                processor.process();
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            } catch (Exception ex) {
                channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, true);
            }

        }
        Thread.sleep(500);
    }

    @Override
    protected void dispose() throws Exception {
        connection.close();
    }
}
