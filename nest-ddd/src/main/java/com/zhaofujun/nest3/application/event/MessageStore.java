package com.zhaofujun.nest3.application.event;


import com.zhaofujun.nest3.application.provider.Provider;

public interface MessageStore extends Provider {

    void save(MessageRecord messageRecord);

    boolean isSucceed(String messageId, String handlerName);

    
}

