package com.jovezhao.nest.rabbitmq;


import com.jovezhao.nest.ddd.event1.AbstractChannelProvider;
import com.jovezhao.nest.ddd.event1.IEventHandler;

/**
 * Created by Jove on 2016-03-22.
 */
public class RabbitChannelProvider extends AbstractChannelProvider {
    private volatile boolean status;

    private MQConnection connection;

    public RabbitChannelProvider(String host,int port,String user,String pwd) {


        connection = new MQConnection(host, port, user, pwd);
    }

    @Override
    public void publish(String eventName, Object data) {
        MQProducer producer = new MQProducer(connection);
        producer.publish(eventName, new EventDataDto(data));
    }



    MQConsumer consumer ;
    @Override
    public void subscribe(String eventName, IEventHandler handler) {

        EventWork work = new EventWork(eventName, handler);
         consumer = new MQConsumer(work, connection);
        Thread thread = new Thread(consumer);
        thread.start();


    }

    @Override
    public void stop() {
        if (consumer != null)
            consumer.stop();

    }
}

