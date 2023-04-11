package com.zhaofujun.nest3.application;


import com.zhaofujun.nest3.ContainerProvider;
import com.zhaofujun.nest3.application.config.CacheConfiguration;
import com.zhaofujun.nest3.application.config.ConfigurationItem;
import com.zhaofujun.nest3.application.context.ServiceContext;
import com.zhaofujun.nest3.application.listener.NestApplicationEventObject;
import com.zhaofujun.nest3.application.listener.NestEventListener;
import com.zhaofujun.nest3.application.listener.NestLifecycle;
import com.zhaofujun.nest3.application.manager.*;
import com.zhaofujun.nest3.application.provider.CacheProvider;
import com.zhaofujun.nest3.application.provider.JsonProvider;
import com.zhaofujun.nest3.application.provider.Provider;
import com.zhaofujun.nest3.impl.ehcache.EhcacheProvider;
import com.zhaofujun.nest3.impl.fastjson.EntityJsonProvider;
import com.zhaofujun.nest3.impl.fastjson.FastjsonProvider;
import com.zhaofujun.nest3.model.Entity;
import com.zhaofujun.nest3.model.Repository;
import com.zhaofujun.nest3.utils.StringUtils;


public class NestApplication {
    private ServiceContextManager serviceContextManager = new ServiceContextManager(this);
    private RepositoryManager repositoryManager = new RepositoryManager(this);
    private ConfigurationManager configurationManager = new ConfigurationManager(this);
    private ProviderManage providerManage = new ProviderManage(this);
    private LifecycleManager lifecycleManager = new LifecycleManager(this);


    NestApplication() {

    }

    private void assembly(ContainerProvider containerProvider) {
        this.configurationManager.addConfigurationItem(containerProvider.getInstances(ConfigurationItem.class));
        this.providerManage.addProvider(containerProvider.getInstances(Provider.class));
        this.lifecycleManager.addListeners(containerProvider.getInstances(NestEventListener.class));
        this.repositoryManager.addRepository(containerProvider.getInstances(Repository.class));
//        this.generatorManager.addLongGenerator(containerProvider.getInstances(LongGenerator.class));
    }

    private static NestApplication application;

    public static NestApplication run(ContainerProvider containerProvider) {
        application = new NestApplication();
        application.start(containerProvider);
        return application;
    }

    public void start(ContainerProvider containerProvider) {
        NestApplicationEventObject beforeStartEventObject = new NestApplicationEventObject(this, this);
        lifecycleManager.publish(NestLifecycle.BeforeStart, beforeStartEventObject);

        this.providerManage.addProvider(new FastjsonProvider(), new EntityJsonProvider(), new EhcacheProvider());
        if (containerProvider != null)
            this.assembly(containerProvider);
        NestApplicationEventObject startedEventObject = new NestApplicationEventObject(this, this);
        lifecycleManager.publish(NestLifecycle.BeforeStart, startedEventObject);
    }

    public static NestApplication current() {
        return application;
    }

    public ServiceContextManager getServiceContextManager() {
        return serviceContextManager;
    }

    public RepositoryManager getRepositoryManager() {
        return repositoryManager;
    }

    public ConfigurationManager getConfigurationManager() {
        return configurationManager;
    }

    public ProviderManage getProviderManage() {
        return providerManage;
    }

    public LifecycleManager getLifecycleManager() {
        return lifecycleManager;
    }

    public static NestApplication getApplication() {
        return application;
    }

    public static ServiceContext currentServiceContext() {
        return application.serviceContextManager.getCurrentContext();
    }


    public <T extends Entity> Repository<T> getRepository(Class<T> tClass) {
        return repositoryManager.getRepository(tClass);
    }

    public void addNestListener(NestEventListener... nestEventListeners) {
        this.lifecycleManager.addListeners(nestEventListeners);
    }


    public CacheClient getCacheClient(String cacheCode) {
        CacheConfiguration cacheConfiguration = configurationManager.getCacheConfigurationByCode(cacheCode);
        CacheProvider cacheProvider = providerManage.get(CacheProvider.class, cacheConfiguration.getCacheProviderCode());
        JsonProvider jsonProvider = providerManage.get(JsonProvider.class, cacheConfiguration.getJsonProviderCode());

        if (cacheProvider == null) cacheProvider = new EhcacheProvider();
        if (jsonProvider == null) jsonProvider = new FastjsonProvider();
        String code = cacheConfiguration.getCode();
        if (StringUtils.isEmpty(code)) code = "default";
        CacheClient cacheClient = new DefaultCacheClient(code, cacheProvider, cacheConfiguration.getIdleSeconds(), jsonProvider);
        return cacheClient;
    }

    public void addRepository(Repository... repositories) {
        this.repositoryManager.addRepository(repositories);
    }
}


