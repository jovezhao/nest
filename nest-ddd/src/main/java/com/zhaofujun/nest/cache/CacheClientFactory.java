package com.zhaofujun.nest.cache;

import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.configuration.ConfigurationManager;
import com.zhaofujun.nest.provider.ProviderManage;

public class CacheClientFactory {

    public static CacheClient getCacheClient(String cacheCode) {
        ConfigurationManager configurationManager = NestApplication.current().getConfigurationManager();
        CacheConfiguration cacheConfiguration = configurationManager.getCacheConfigurationByCode(cacheCode);

        ProviderManage providerManage = NestApplication.current().getProviderManage();
        CacheProvider cacheProvider = providerManage.getCacheProvider(cacheConfiguration.getProviderCode());

        CacheClient cacheClient = new DefaultCacheClient(cacheProvider, cacheConfiguration);
        return cacheClient;
    }
}
