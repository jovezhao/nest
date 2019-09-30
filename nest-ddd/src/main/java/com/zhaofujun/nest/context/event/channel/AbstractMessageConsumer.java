package com.zhaofujun.nest.context.event.channel;

import com.zhaofujun.nest.container.BeanFinder;
import com.zhaofujun.nest.context.event.EventArgs;
import com.zhaofujun.nest.context.event.EventHandler;
import com.zhaofujun.nest.context.event.EventData;
import com.zhaofujun.nest.context.event.message.MessageConverter;
import com.zhaofujun.nest.context.event.message.MessageInfo;
import com.zhaofujun.nest.context.event.message.MessageRecord;
import com.zhaofujun.nest.context.event.store.MessageStore;
import com.zhaofujun.nest.context.event.store.MessageStoreFactory;

import java.util.Date;
import java.util.UUID;

public abstract class AbstractMessageConsumer implements MessageConsumer {

    private BeanFinder beanFinder;

    public AbstractMessageConsumer(BeanFinder beanFinder) {
        this.beanFinder = beanFinder;
    }

    public abstract void subscribe(EventHandler eventHandler);

    protected void onReceivedMessage(MessageInfo messageInfo, EventHandler eventHandler, Object context) {
        EventData eventData = MessageConverter.toEventData(messageInfo, eventHandler.getEventDataClass());

        MessageRecord record = new MessageRecord();
        record.setHandlerName(eventHandler.getClass().getName());
        record.setHandleTime(new Date());
        record.setId(UUID.randomUUID().toString());
        record.setMessageInfo(messageInfo);
        record.setReceiveTime(new Date());

        EventArgs eventArgs = new EventArgs();
        eventArgs.setEventCode(eventHandler.getEventCode());
        eventArgs.setSource(messageInfo.getEventSource());
        eventArgs.setMessageId(messageInfo.getMessageId());
        eventArgs.setReceiveTime(new Date());
        eventArgs.setSendTime(messageInfo.getSendTime());


        MessageStoreFactory messageStoreFactory = new MessageStoreFactory(beanFinder);

        MessageStore messageStore = messageStoreFactory.create();
        if (!messageStore.isSucceed(messageInfo.getMessageId(), eventHandler.getClass().getName())){
            System.err.println("消息已被消费");
            return;
        }else{
            messageStore.save(record);
        }
        try {

            eventHandler.handle(eventData, eventArgs);
            record.setRecordState(MessageRecord.RecordState.SUCCESS);
        } catch (Exception ex) {
            record.setRecordState(MessageRecord.RecordState.ERROR);
            onFailed(eventHandler, context, ex);
        } finally {

            messageStore.save(record);

            onEnds(eventHandler, context);
        }
    }


    protected abstract void onFailed(EventHandler eventHandler, Object context, Exception ex);

    protected abstract void onEnds(EventHandler eventHandler, Object context);
}
