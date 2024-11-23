package com.zhaofujun.nest.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.zhaofujun.nest.NestEngine;
import com.zhaofujun.nest.ddd.event.EventAppService;
import com.zhaofujun.nest.inner.DefaultEventInfoRepository;
import com.zhaofujun.nest.utils.AppServiceUtil;

public class UserTest {

    @BeforeAll
    public void initEngine() throws Throwable {
        NestEngine nestEngine = new NestEngine();

        EventAppService eventAppService = AppServiceUtil.create(EventAppService.class);
        eventAppService.setQuery(new DefaultEventInfoRepository());

        nestEngine.setEventAppService(eventAppService);

        // 注册事件处理器
        nestEngine.registerEventHandler(new UserCreatedHandler());

        // 启动引擎
        nestEngine.start();

    }

    @Test
    public void createUser() throws Throwable {

        // 创建化用户应用服务
        DefaultUserAppService userAppService = AppServiceUtil.create(DefaultUserAppService.class);
        UserDto userDto = userAppService.create("1111", "Li lei");

        assertEquals("111", userDto.getId());
        assertEquals("Li lei", userDto.getName());

    }

}
