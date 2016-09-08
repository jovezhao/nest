package com.ywkj.nest.rabbitmq;



import com.ywkj.nest.core.exception.GeneralException;
import com.ywkj.nest.core.log.ILog;
import com.ywkj.nest.core.log.LogAdapter;
import org.springframework.util.SerializationUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

import java.io.Serializable;

/**
 * Created by Jove on 2016-02-29.
 */
class MQConsumer {
    ILog logger = new LogAdapter(MQConsumer.class);
    private EventWork work;
    private MQConnection connection;
    private final static int  prefetchCount = 5;
    public MQConsumer(EventWork work, MQConnection connection) {

        this.work = work;
        this.connection = connection;

    }

    public void run() {
        Channel channel=null;
        try {
            //创建消息者
            channel = connection.getConnection().createChannel();
            channel.exchangeDeclare(work.getEventName(), "fanout", true, false, null);
            channel.basicQos(prefetchCount);
            //申明消息队列
            channel.queueDeclare(work.getHandlerName(), true, false, false, null);
            channel.queueBind(work.getHandlerName(), work.getEventName(), "");

            QueueingConsumer consumer = new QueueingConsumer(channel);
            // 第二个参数必须为false，设置必须要ack相应
            channel.basicConsume(work.getHandlerName(), false, consumer);
            //设置每次都把当前要取的数据取完再关闭当前channel
            for (int i = 0; i < prefetchCount; i++) {
                    QueueingConsumer.Delivery delivery = consumer.nextDelivery();

                    Serializable object = (Serializable) SerializationUtils.deserialize(delivery.getBody());

                    boolean flag = false;
                    try {
                        flag = work.doWork(object);
                    } catch (GeneralException e) {
                        //处理消息失败
                        logger.error(e);
                    } catch (Exception e) {
                        //处理消息失败
                        logger.fatal(e);
                    }
                    if (flag) {
                        channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                    } else {
                        Thread.sleep(1000);
                        channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, true);
                        break;
                    }
             }
        } catch (Exception e) {
            logger.fatal(e);
        }finally {
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
