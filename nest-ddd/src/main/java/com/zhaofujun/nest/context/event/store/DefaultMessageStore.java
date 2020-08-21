package com.zhaofujun.nest.context.event.store;

import com.zhaofujun.nest.cache.CacheClient;
import com.zhaofujun.nest.cache.CacheClientFactory;
import com.zhaofujun.nest.context.event.message.MessageRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DefaultMessageStore implements MessageStoreProvider {
    private Logger logger = LoggerFactory.getLogger(DefaultMessageStore.class);
    public static final String CACHE_CODE = "DefaultMessageStore";
    private CacheClient cacheClient;

    public DefaultMessageStore() {
        this.cacheClient = CacheClientFactory.getCacheClient(CACHE_CODE);
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


    @Override
    public String getCode() {
        return "DefaultMessageStore";
    }
}
