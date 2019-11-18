package com.zhaofujun.nest.context.event.store;

import com.zhaofujun.nest.core.CacheClient;
import com.zhaofujun.nest.cache.CacheClientFactory;
import com.zhaofujun.nest.core.BeanFinder;
import com.zhaofujun.nest.context.event.message.MessageRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DefaultMessageStore implements MessageStore {
    private Logger logger = LoggerFactory.getLogger(DefaultMessageStore.class);
    public static final String CACHE_CODE = "DefaultMessageStore";
    private CacheClient cacheClient;

    public DefaultMessageStore(BeanFinder beanFinder) {
        CacheClientFactory cacheClientFactory = new CacheClientFactory(beanFinder);
        this.cacheClient = cacheClientFactory.getCacheClient(CACHE_CODE);
    }

    @Override
    public void save(MessageRecord messageRecord) {
        try {
            cacheClient.put(messageRecord.getMessageInfo().getMessageId() + "-" + messageRecord.getHandlerName(), messageRecord);
        } catch (Exception ex) {
            logger.warn("缓存通道异常", ex);
        }
    }


    @Override
    public boolean isSucceed(String messageId, String handlerName) {
        try {
            return cacheClient.containsKey(messageId + "-" + handlerName);
        } catch (Exception ex) {
            logger.warn("缓存通道异常", ex);
            return false;
        }
    }


}
