package com.zhaofujun.nest3.application.context;

import com.zhaofujun.nest3.application.event.MessageInfo;
import com.zhaofujun.nest3.application.provider.Provider;

import java.time.LocalDateTime;
import java.util.Collection;

public interface MessageStore extends Provider {
    void saveAndDoing(Collection<MessageInfo> messageInfos);

    void done(Collection<MessageInfo> messageInfos);

    void fail(Collection<MessageInfo> messageInfos);

    Collection<MessageInfo> getAndDoing(LocalDateTime endTime);
}
