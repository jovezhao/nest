package com.zhaofujun.nest.context.event.delay;

import com.zhaofujun.nest.context.appservice.ApplicationServiceCreator;
import com.zhaofujun.nest.context.event.ResendAppService;
import com.zhaofujun.nest.context.event.message.MessageBacklog;
import com.zhaofujun.nest.standard.EventData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.TimerTask;

public class DelayTimerTask extends TimerTask {
    private Logger logger = LoggerFactory.getLogger(DelayTimerTask.class);

    @Override
    public void run() {
        DelayMessageStore delayMessageStore = DelayMessageStoreFactory.create();
        List<MessageBacklog> messageBacklogs = delayMessageStore.getAndLock();
        messageBacklogs.forEach(messageBacklog -> {
            if (messageBacklog != null) {
                logger.debug("重新投递消息" + messageBacklog.getEventCode());
                EventData eventData = messageBacklog.getMessageInfo().getData();
                ResendAppService appService = ApplicationServiceCreator.create(ResendAppService.class);
                appService.send(eventData);
                delayMessageStore.clear(messageBacklog.getMessageInfo().getMessageId());
            }
        });
    }
}

