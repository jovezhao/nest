package com.jovezhao.nest.ddd.event.provider.distribut;

import com.jovezhao.nest.ddd.event.EventHandler;
import com.jovezhao.nest.utils.JsonUtils;

/**
 * 消息处理器
 * 主要用于处理幂等性并发起事件处理器回调
 * Created by zhaofujun on 2017/6/22.
 */
public class MessageProcessor {
    private MessageData messageData;
    private EventHandler eventHandler;

    public MessageProcessor(EventHandler eventHandler) {

        this.eventHandler = eventHandler;
    }

    public void setMessageData(String messageData) {
        this.messageData = JsonUtils.toObj(messageData,MessageData.class);
    }

    public void process() throws Exception {

        if (EventRecordManager.isProcess(messageData.getMessageId())) {
            EventRecordManager.putEventData(messageData);
            return;
        }


        //从消息日志中查看是否有处理过本消息，如果已经处理则直接跳出
        Object data = JsonUtils.toObj(messageData.getMessage(), eventHandler.getTClass());
        eventHandler.handle(data);
        EventRecordManager.putEventData(messageData);
    }


}
