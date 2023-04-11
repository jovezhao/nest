package com.zhaofujun.nest3.application.event;

import com.zhaofujun.nest3.application.provider.MessageChannelProvider;
import com.zhaofujun.nest3.impl.observer.MessageProcessor;
import com.zhaofujun.nest3.model.EventHandler;

/**
 * 消息控制器,为每一个消息通道建立一个控制器。
 * <p>
 * 控制器主要用于管理消息通道的连接和绑定回调函数
 */
public class MessageController extends Thread {

    private MessageChannelProvider messageChannelProvider;


    public MessageChannelProvider getMessageChannelProvider() {
        return messageChannelProvider;
    }

    public MessageController(ThreadGroup group, String name, MessageChannelProvider messageChannelProvider) {
        super(group, name);
        this.messageChannelProvider = messageChannelProvider;
    }

    @Override
    public void run() {
        messageChannelProvider.start();
    }


    public void subscribe(EventHandler eventHandler) {

        MessageProcessor messageProcessor=new MessageProcessor(eventHandler,null);

        messageChannelProvider.subscribe(eventHandler.getEventDataClass().getName(),eventHandler.getEventCode() ,messageProcessor );
    }
}
