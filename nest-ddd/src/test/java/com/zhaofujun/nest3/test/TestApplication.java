package com.zhaofujun.nest3.test;

import com.zhaofujun.nest3.application.NestApplication;
import com.zhaofujun.nest3.application.UserAppService;
import com.zhaofujun.nest3.application.UserRepository;
import com.zhaofujun.nest3.application.WalletRepository;
import com.zhaofujun.nest3.application.config.CacheConfiguration;
import com.zhaofujun.nest3.application.context.ApplicationServiceCreator;
import com.zhaofujun.nest3.utils.EntityUtils;
import org.junit.Before;
import org.junit.Test;

public class TestApplication {
    UserAppService userAppService;

    @Before
    public void start() {
        NestApplication current = NestApplication.run(null);
        current.addRepository(new UserRepository(),new WalletRepository());

        CacheConfiguration cacheConfiguration = new CacheConfiguration();
        cacheConfiguration.setCode(EntityUtils.EntityCacheCode);
        cacheConfiguration.setCacheProviderCode(EntityUtils.EntityCacheProviderCode);
        cacheConfiguration.setJsonProviderCode(EntityUtils.EntityJsonProviderCode);
        cacheConfiguration.setIdleSeconds(12 * 60);
        current.getConfigurationManager().addConfigurationItem(cacheConfiguration);
        userAppService = ApplicationServiceCreator.create(UserAppService.class);

    }

    @Test
    public void testCreate() {

        userAppService.create();
        userAppService.create();
        userAppService.create();
    }

    @Test
    public void testUpdate() {
        userAppService.update();
        userAppService.update();
        userAppService.update();
    }
}
