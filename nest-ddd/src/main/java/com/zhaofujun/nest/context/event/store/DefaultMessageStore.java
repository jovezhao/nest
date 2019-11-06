package com.zhaofujun.nest.context.event.store;

import com.zhaofujun.nest.cache.CacheClient;
import com.zhaofujun.nest.cache.CacheClientFactory;
import com.zhaofujun.nest.container.BeanFinder;
import com.zhaofujun.nest.context.event.message.MessageRecord;


public class DefaultMessageStore implements MessageStore {

    private static final String CODE = "DefaultMessageStore";
    private CacheClient cacheClient;

    public DefaultMessageStore(BeanFinder beanFinder) {
        CacheClientFactory cacheClientFactory = new CacheClientFactory(beanFinder);
        this.cacheClient = cacheClientFactory.getCacheClient(CODE);
    }

    @Override
    public void save(MessageRecord messageRecord) {
        cacheClient.put(messageRecord.getMessageInfo().getMessageId() + "-" + messageRecord.getHandlerName(), messageRecord);

    }


    @Override
    public boolean isSucceed(String messageId, String handlerName) {
        return cacheClient.containsKey(messageId + "-" + handlerName);

    }


}
