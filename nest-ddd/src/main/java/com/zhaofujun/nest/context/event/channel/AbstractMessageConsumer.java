package com.zhaofujun.nest.context.event.channel;

import com.zhaofujun.nest.context.event.message.MessageConverterFactory;
import com.zhaofujun.nest.context.event.message.MessageInfo;
import com.zhaofujun.nest.context.event.message.MessageRecord;
import com.zhaofujun.nest.context.event.store.MessageStore;
import com.zhaofujun.nest.context.event.store.MessageStoreFactory;
import com.zhaofujun.nest.exception.OtherCustomException;
import com.zhaofujun.nest.standard.*;

import java.util.Date;
import java.util.UUID;

public abstract class AbstractMessageConsumer implements MessageConsumer {

    public abstract void subscribe(EventHandler eventHandler);

    protected void onReceivedMessage(String messageInfoString, EventHandler eventHandler, Object context) {

        MessageInfo messageInfo = MessageConverterFactory.create().jsonToMessage(messageInfoString, eventHandler.getEventDataClass());
        EventData eventData = messageInfo.getData();

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


        MessageStore messageStore = MessageStoreFactory.create();
        if (messageStore.isSucceed(messageInfo.getMessageId(), eventHandler.getClass().getName()))
            return;


        try {

            eventHandler.handle(eventData, eventArgs);
            messageStore.save(record);
        } catch (CustomException ex) {
            eventHandler.onCustomException(context, ex);
        } catch (SystemException ex) {
            onFailed(eventHandler, context, ex);
            eventHandler.onSystemException(context, ex);
            throw ex;
        } catch (Exception ex) {

            if (ex instanceof CustomExceptionable) {
                //业务异常
                OtherCustomException customException = new OtherCustomException(ex.getMessage(), ex);
                eventHandler.onCustomException(context, customException);
            } else {

                onFailed(eventHandler, context, ex);
                SystemException systemException = new SystemException("事件处理器发生系统异常", ex);
                eventHandler.onSystemException(context, systemException);
                throw systemException;
            }
        } finally {
            onEnds(eventHandler, context);
        }
    }


    protected void onFailed(EventHandler eventHandler, Object context, Exception ex) {
    }

    protected void onEnds(EventHandler eventHandler, Object context) {
    }
}
