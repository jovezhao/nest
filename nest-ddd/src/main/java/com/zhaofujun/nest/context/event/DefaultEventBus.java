package com.zhaofujun.nest.context.event;

import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.context.appservice.ServiceContextManager;
import com.zhaofujun.nest.context.event.channel.MessageChannelProvider;
import com.zhaofujun.nest.context.event.channel.MessageChannelProviderFactory;
import com.zhaofujun.nest.context.event.channel.MessageConsumer;
import com.zhaofujun.nest.context.event.channel.MessageProducer;
import com.zhaofujun.nest.context.event.delay.DelayMessageBacklog;
import com.zhaofujun.nest.context.event.delay.DelayMessageStore;
import com.zhaofujun.nest.context.event.delay.DelayMessageStoreFactory;
import com.zhaofujun.nest.context.event.message.MessageBacklog;
import com.zhaofujun.nest.context.event.message.MessageConverterFactory;
import com.zhaofujun.nest.context.event.message.MessageInfo;
import com.zhaofujun.nest.standard.EventBus;
import com.zhaofujun.nest.standard.EventData;
import com.zhaofujun.nest.standard.EventHandler;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public class DefaultEventBus implements EventBus {


    @Override
    public void publish(EventData eventData, String eventSource, long delaySecond) {


        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setMessageId(UUID.randomUUID().toString());
        messageInfo.setData(eventData);
        messageInfo.setEventSource(eventSource);
        messageInfo.setSendTime(new Date());

        String messageString= MessageConverterFactory.create().messageToString(messageInfo);
        if (delaySecond == 0) {
            EventConfiguration eventConfiguration = getEventConfigurationByEventCode(eventData.getEventCode());
            MessageChannelProvider messageChannel = MessageChannelProviderFactory.create(eventConfiguration.getMessageChannelCode());
            MessageProducer messageProducer = messageChannel.getMessageProducer();
            messageProducer.send(eventData.getEventCode(), messageInfo);
        } else {
            //延时事件先提交到当前上下文，待事务提交后再save到DelayMessageStore，保证事务提交成功再发送消息
            ServiceContextManager.get().addDelayMessageBacklog(new DelayMessageBacklog(new MessageBacklog(eventData.getEventCode(), messageString, eventData.getClass().getName(), messageInfo.getMessageId()), LocalDateTime.now().plusSeconds(delaySecond)));
        }
    }

    private EventConfiguration getEventConfigurationByEventCode(String eventCode) {
        return NestApplication.current().getConfigurationManager().getEventConfigurationByEventCode(eventCode);
    }


    @Override
    public void registerHandler(EventHandler eventHandler) {

        EventConfiguration eventConfiguration = getEventConfigurationByEventCode(eventHandler.getEventCode());

        MessageChannelProvider messageChannel = MessageChannelProviderFactory.create(eventConfiguration.getMessageChannelCode());

        MessageConsumer messageConsumer = messageChannel.getMessageConsumer();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                messageConsumer.subscribe(eventHandler);
            }
        });
        thread.start();

    }

}
