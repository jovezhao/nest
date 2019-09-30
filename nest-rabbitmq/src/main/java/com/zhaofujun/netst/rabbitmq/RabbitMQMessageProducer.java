package com.zhaofujun.netst.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.zhaofujun.nest.SystemException;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageProducer;
import com.zhaofujun.nest.context.event.message.MessageInfo;
import com.zhaofujun.nest.utils.JsonUtils;

/**
 *
 **/
public class RabbitMQMessageProducer extends DistributeMessageProducer {

    private ConnectionFactory connectionFactory;

    private  Connection connection=null;

    private  Channel channel=null;

    public RabbitMQMessageProducer(RabbitMQProviderConfig rabbitMQProviderConfig){
        this.connectionFactory=new ConnectionFactory();
        this.connectionFactory.setAutomaticRecoveryEnabled(true);
        this.connectionFactory.setHost(rabbitMQProviderConfig.getHost());
        this.connectionFactory.setPort(rabbitMQProviderConfig.getPort());
        this.connectionFactory.setUsername(rabbitMQProviderConfig.getUser());
        this.connectionFactory.setPassword(rabbitMQProviderConfig.getPwd());
    }


    @Override
    public void commit(String messageGroup, MessageInfo messageInfo) {
        try {
            connection = connectionFactory.newConnection();
            channel= connection.createChannel();
            channel.exchangeDeclare(messageGroup,"fanout",true,false,null);
            channel.basicPublish(messageGroup, "", MessageProperties.PERSISTENT_TEXT_PLAIN, JsonUtils.toJsonString(messageInfo).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            throw new SystemException("发送RabbitMQ消息失败", e);
        } finally {
            destruction();
        }
    }

    @Override
    public void destruction() {
        try {
            channel.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new SystemException("关闭RabbitMQ消息通道和链接失败", e);
        }
    }
}
