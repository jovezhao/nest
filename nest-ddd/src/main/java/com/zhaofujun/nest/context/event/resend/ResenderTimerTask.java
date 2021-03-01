package com.zhaofujun.nest.context.event.resend;

import com.zhaofujun.nest.context.appservice.ApplicationServiceCreator;
import com.zhaofujun.nest.context.event.ResendAppService;
import com.zhaofujun.nest.context.event.message.MessageBacklog;
import com.zhaofujun.nest.context.event.message.MessageInfo;
import com.zhaofujun.nest.json.JsonCreator;
import com.zhaofujun.nest.json.ParameterizedTypeFactory;
import com.zhaofujun.nest.standard.EventData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

public class ResenderTimerTask extends TimerTask {
    private Logger logger = LoggerFactory.getLogger(ResenderTimerTask.class);

    @Override
    public void run() {
        MessageBacklog messageBacklog = MessageResendFactory.create().pollOne();
        if (messageBacklog != null) {
            logger.debug("重新投递消息" + messageBacklog.getEventCode());
            Class eventDataType = null;
            try {
                eventDataType = Class.forName(messageBacklog.getEventDataType());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return;
            }

            MessageInfo messageInfo = JsonCreator.getInstance().toObj(messageBacklog.getMessageInfoString(), ParameterizedTypeFactory.make(MessageInfo.class, eventDataType));

            EventData eventData = messageInfo.getData();
            ResendAppService appService = ApplicationServiceCreator.create(ResendAppService.class);
            appService.send(eventData);
        }
    }
}

