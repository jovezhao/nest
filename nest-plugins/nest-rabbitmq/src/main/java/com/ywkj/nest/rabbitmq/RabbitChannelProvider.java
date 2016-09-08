package com.ywkj.nest.rabbitmq;


import com.ywkj.nest.ddd.AbstractChannelProvider;
import com.ywkj.nest.ddd.IEventHandler;

import java.util.Properties;

/**
 * Created by Jove on 2016-03-22.
 */
public class RabbitChannelProvider extends AbstractChannelProvider {
    private volatile boolean status;

    private MQConnection connection;

   public RabbitChannelProvider(Properties properties){
       this.properties=properties;
       String host=properties.getProperty("host");
       int port=Integer.parseInt(properties.getProperty("port","5672"));
       String user=properties.getProperty("user");
       String pwd=properties.getProperty("pwd");
       connection=new MQConnection(host,port,user,pwd);
   }

    @Override
    public void publish(String eventName, Object data) {
        MQProducer producer = new MQProducer(connection);
        producer.publish(eventName, new EventDataDto(data));
    }

    @Override
    public void subscribe(String eventName, IEventHandler handler) {
        status = true;
        EventWork work = new EventWork(eventName, handler);
        final MQConsumer consumer = new MQConsumer(work, connection);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (status) {
                    consumer.run();
                }
            }
        });
        thread.start();


    }

    @Override
    public void stop() {
        status = false;

    }
}

