//package com.jovezhao.nest.activemq;
//
//import com.jovezhao.nest.exception.SystemException;
//import com.jovezhao.nest.ddd.event1.AbstractChannelProvider;
//import com.jovezhao.nest.ddd.event1.IEventHandler;
//import org.apache.activemq.pool.PooledConnectionFactory;
//
//import javax.jms.JMSException;
//
///**
// * Created by Jove on 2016/7/27.
// */
//
//public class ActiveChannelProvider extends AbstractChannelProvider {
//
//
//    private int prefetchCount;
//    private volatile boolean status;
//    PooledConnectionFactory connectionFactory;
//    private ActiveMQProducer producer;
//    private ActiveMQConsumer consumer;
//
//    public ActiveChannelProvider(String brokers, int prefetchCount) {
//        connectionFactory = new PooledConnectionFactory(brokers);
//        connectionFactory.start();
//        producer = new ActiveMQProducer(connectionFactory);
//        this.prefetchCount = prefetchCount;
//    }
//
//    @Override
//    public void publish(String eventName, Object data) {
//        EventDataDto dto = new EventDataDto(data);
//        try {
//            producer.publish(eventName, dto);
//        } catch (JMSException e) {
//            e.printStackTrace();
//            throw new SystemException(e);
//        }
//
//    }
//
//
//    @Override
//    public void subscribe(String eventName, IEventHandler handler) {
//        consumer = new ActiveMQConsumer(connectionFactory, new EventWork(eventName, handler), prefetchCount);
//        status = true;
//        Thread workThread = new Thread(consumer);
//        workThread.start();
//
//    }
//
//    @Override
//    public void stop() {
//        connectionFactory.stop();
//        if (consumer != null)
//            consumer.stop();
//    }
//}
