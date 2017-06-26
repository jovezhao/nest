package com.jovezhao.nest.rabbitmq;

import com.jovezhao.nest.ddd.event.provider.distribut.DistributedEventConsumer;
import com.jovezhao.nest.ddd.event.provider.distribut.MessageProcessor;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

/**
 * Created by zhaofujun on 2017/6/22.
 */
public class RebbitMQEventConsumer extends DistributedEventConsumer<RabbitMQProviderConfig> {
    ConnectionFactory connectionFactory;

    public RebbitMQEventConsumer(ConnectionFactory connectionFactory) {
        this.connectionFactory=connectionFactory;
    }


    @Override
    protected void consume(MessageProcessor processor) throws Exception {
        Connection connection = connectionFactory.newConnection();
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
            processor.setMessageData(json);
            try {
                processor.process();
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            } catch (Exception ex) {
                channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, true);
            }

        }
        channel.close();
        connection.close();
        Thread.sleep(500);
    }


}
