package com.zhaofujun.nest.context.event.delay;


import com.zhaofujun.nest.context.event.message.MessageBacklog;
import com.zhaofujun.nest.provider.Provider;

import java.util.List;

public interface DelayMessageStore extends Provider {

    void add(DelayMessageBacklog delayMessageBacklog);

    List<MessageBacklog> getAndLock();

    void  clear(String messageId);
}
