package com.zhaofujun.nest.test;

import java.lang.reflect.InvocationTargetException;

import com.zhaofujun.nest.NestEngine;
import com.zhaofujun.nest.ddd.event.EventAppService;
import com.zhaofujun.nest.inner.DefaultEventInfoRepository;
import com.zhaofujun.nest.utils.AppServiceUtil;

public class NestTest {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException  {
        NestEngine nestEngine = new NestEngine();

        EventAppService eventAppService = AppServiceUtil.create(EventAppService.class);
        eventAppService.setQuery(new DefaultEventInfoRepository());

        nestEngine.setEventAppService(eventAppService);
        nestEngine.start();

        UserAppService userAppService = AppServiceUtil.create(UserAppService.class);
        userAppService.test();
        userAppService.update();

    }
}
