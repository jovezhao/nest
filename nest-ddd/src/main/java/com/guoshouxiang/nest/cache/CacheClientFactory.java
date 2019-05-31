package com.guoshouxiang.nest.cache;

import com.guoshouxiang.nest.cache.provider.CacheProvider;
import com.guoshouxiang.nest.cache.provider.DefaultCacheProvider;
import com.guoshouxiang.nest.configuration.CacheConfiguration;
import com.guoshouxiang.nest.configuration.ConfigurationManager;
import com.guoshouxiang.nest.container.BeanFinder;

public class CacheClientFactory {

    private BeanFinder beanFinder;

    public CacheClientFactory(BeanFinder beanFinder) {
        this.beanFinder = beanFinder;
    }

    public CacheClient getCacheClient(String cacheCode) {
        ConfigurationManager configurationManager = new ConfigurationManager(beanFinder);
        CacheConfiguration cacheConfiguration = configurationManager.getCacheConfigurationByCode(cacheCode);
        CacheProvider beanProvider = beanFinder.getInstance(CacheProvider.class, cacheConfiguration.getProviderCode());
        if (beanProvider == null)
            beanProvider = new DefaultCacheProvider();
        CacheClient cacheClient = new CacheClient(beanProvider, cacheConfiguration);
        return cacheClient;
    }
}
