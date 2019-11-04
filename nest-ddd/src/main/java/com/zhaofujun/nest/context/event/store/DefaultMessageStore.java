package com.zhaofujun.nest.context.event.store;

import com.zhaofujun.nest.cache.provider.CacheProvider;
import com.zhaofujun.nest.cache.provider.DefaultCacheProvider;
import com.zhaofujun.nest.configuration.CacheConfiguration;
import com.zhaofujun.nest.configuration.ConfigurationManager;
import com.zhaofujun.nest.container.BeanFinder;
import com.zhaofujun.nest.context.event.message.MessageRecord;
import com.zhaofujun.nest.utils.JsonUtils;
import com.zhaofujun.nest.utils.StringUtils;

import java.util.Set;

public class DefaultMessageStore implements MessageStore {

    private BeanFinder beanFinder;

    public DefaultMessageStore(BeanFinder beanFinder){
        this.beanFinder=beanFinder;
    }

    @Override
    public void save(MessageRecord messageRecord) {

        CacheConfiguration cacheConfiguration = this.cacheConfiguration();
        CacheProvider cacheProvider = this.cacheProvider(cacheConfiguration.getProviderCode());
        cacheProvider.put(this.groupName(cacheConfiguration),messageRecord.getId(), JsonUtils.toJsonString(messageRecord),cacheConfiguration.getIdleSeconds());
    }


    private String groupName(CacheConfiguration cacheConfiguration){
        StringBuffer sb=new StringBuffer(cacheConfiguration.getName()).append("-").append("message");
        return sb.toString();

    }

    @Override
    public boolean isSucceed(String messageId, String handlerName) {


        CacheConfiguration cacheConfiguration = this.cacheConfiguration();
        CacheProvider cacheProvider = this.cacheProvider(cacheConfiguration.getProviderCode());
        MessageRecord messageRecord = cacheProvider.get(this.groupName(cacheConfiguration),messageId,MessageRecord.class);
        if(null !=messageRecord){
            return false;
        }
        return true;
    }

    /**
     * 获取缓存配置
     * @return
     */
    private CacheConfiguration cacheConfiguration(){
        ConfigurationManager instance =new ConfigurationManager(beanFinder);
        Set<CacheConfiguration> cacheConfiguration = instance.getCacheConfiguration();
        if(cacheConfiguration.size()>1){
          return   cacheConfiguration.stream().filter(n->!n.getProviderCode().equals(DefaultCacheProvider.CODE)).findFirst().orElse(null);
        }
        return cacheConfiguration.stream().findFirst().orElse(null);

    }

    /**
     * 获取缓存策略
     * @param providerCode:策略code
     * @return
     */
    private CacheProvider cacheProvider(String providerCode){
        Set<CacheProvider> instances = beanFinder.getInstances(CacheProvider.class);
        CacheProvider cacheProvider= null;
        if(StringUtils.isEmpty(providerCode)){
            cacheProvider = instances.stream().filter(n->n.getCode().equals(DefaultCacheProvider.CODE)).findFirst().orElse(null);
        }else{
            cacheProvider=instances.stream().filter(n->n.getCode().equals(providerCode)).findFirst().orElse(null);
        }
        return cacheProvider;
    }
}
