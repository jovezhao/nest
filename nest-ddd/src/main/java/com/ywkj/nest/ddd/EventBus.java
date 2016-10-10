package com.ywkj.nest.ddd;


import com.ywkj.nest.core.log.ILog;
import com.ywkj.nest.core.log.LogAdapter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 事件总线发布器
 * Created by Jove on 2016-03-21.
 */
public class EventBus {

    private ILog logger = new LogAdapter(this.getClass());
    //通过注入的发布器策略指定事件总线通道，需要默认一种内存总线通道以提高系统内事件响应速度
    //@Autowired
    private AbstractChannelProvider provider;

    public void setProvider(AbstractChannelProvider provider) {
        this.provider = provider;
    }

    /**
     * 发布一个事件
     *
     * @param event
     */
    public void publish(ServiceEvent event) {
        logger.info("发布事件", event.getEventName(), event.getData());
        provider.publish(event.getEventName(), event.getData());
    }


    /**
     * 向总线注册一个handler<p>
     * 注册的handler会自动订阅指定的事件并且处理事件
     *
     * @param handler
     */
    public void registerHandler(IEventHandler handler) {
        provider.subscribe(handler.getEventName(), handler);
    }
}
