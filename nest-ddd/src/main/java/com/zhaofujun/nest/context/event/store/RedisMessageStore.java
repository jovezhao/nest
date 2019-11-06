package com.zhaofujun.nest.context.event.store;

import com.zhaofujun.nest.cache.CacheClient;
import com.zhaofujun.nest.cache.CacheClientFactory;
import com.zhaofujun.nest.container.BeanFinder;
import com.zhaofujun.nest.context.event.message.MessageRecord;

/**
 * RedisMessageStore
 **/
public class RedisMessageStore implements MessageStore {

    private CacheClient cacheClient;

    public RedisMessageStore(BeanFinder beanFinder,String cacheProviderCode) {
        CacheClientFactory cacheClientFactory = new CacheClientFactory(beanFinder);
        this.cacheClient = cacheClientFactory.getCacheClient(cacheProviderCode);
    }
    @Override
    public void save(MessageRecord messageRecord) {
        //保存
        cacheClient.put(getKey(messageRecord.getId(),messageRecord.getHandlerName()),messageRecord);
    }

    private String getKey(String messageId,String handlerName){
        return handlerName + "-" +messageId;
    }

    @Override
    public boolean isSucceed(String messageId, String handlerName) {
      return  cacheClient.containsKey(this.getKey(messageId, handlerName));
    }
}
