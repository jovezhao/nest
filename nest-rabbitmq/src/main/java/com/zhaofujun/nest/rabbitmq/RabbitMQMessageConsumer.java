package com.zhaofujun.nest.rabbitmq;

import com.rabbitmq.client.*;
import com.zhaofujun.nest.SystemException;
import com.zhaofujun.nest.container.BeanFinder;
import com.zhaofujun.nest.context.event.EventHandler;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageConsumer;
import com.zhaofujun.nest.context.event.message.MessageInfo;
import com.zhaofujun.nest.utils.JsonUtils;

import java.io.IOException;
import java.util.Map;

/**
 *
 **/
public class RabbitMQMessageConsumer extends DistributeMessageConsumer {

    private ConnectionFactory connectionFactory;

    private Connection connection=null;

    private Channel channel=null;

    private RabbitMQProviderConfig rabbitMQProviderConfig;

    private BuiltinExchangeType exchangeType;

    private String exchangeName;

    private String routingKey;

    private Map<String,Object> arguments;

    public RabbitMQMessageConsumer(BeanFinder beanFinder,RabbitMQProviderConfig rabbitMQProviderConfig) {
        super(beanFinder);
        this.connectionFactory=new ConnectionFactory();
        this.connectionFactory.setAutomaticRecoveryEnabled(true);
        this.connectionFactory.setHost(rabbitMQProviderConfig.getHost());
        this.connectionFactory.setPort(rabbitMQProviderConfig.getPort());
        this.connectionFactory.setUsername(rabbitMQProviderConfig.getUser());
        this.connectionFactory.setPassword(rabbitMQProviderConfig.getPwd());
        this.exchangeType=rabbitMQProviderConfig.getExchangeType();
        this.exchangeName=rabbitMQProviderConfig.getExchangeName();
        this.routingKey=rabbitMQProviderConfig.getRoutingKey();
        this.arguments=rabbitMQProviderConfig.getArguments();
        this.rabbitMQProviderConfig=rabbitMQProviderConfig;
        try {
            this.connection = connectionFactory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
            throw new SystemException("创建RabbitMQ客户端链接失败",e);
        }

    }

    @Override
    public void subscribe(EventHandler eventHandler) {

        try {
            this.channel = connection.createChannel();
            //申明交换机
            channel.basicQos(rabbitMQProviderConfig.getPrefetchCount());
            //申明消息队列
            channel.queueDeclare(eventHandler.getEventCode(), false, false, false, null);
            channel.queueBind(eventHandler.getEventCode(), this.exchangeName, this.routingKey);
            DefaultConsumer  consumer=new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String msg = new String(body);
                    MessageInfo messageInfo = JsonUtils.toObj(msg, MessageInfo.class);
                    try {
                        onReceivedMessage(messageInfo,eventHandler,channel);
                        //手动进行ack操作
                        channel.basicAck(envelope.getDeliveryTag(),false);
                    }catch (Exception e){
                        //消息被拒绝
                        channel.basicNack(envelope.getDeliveryTag(), false, false);
                        e.printStackTrace();
                    }
                }
            };
            // 第二个参数必须为false，设置必须要ack相应
            channel.basicConsume(eventHandler.getEventCode(), false, consumer);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onFailed(EventHandler eventHandler, Object context, Exception ex) {

    }

    @Override
    protected void onEnds(EventHandler eventHandler, Object context) {

    }
}
