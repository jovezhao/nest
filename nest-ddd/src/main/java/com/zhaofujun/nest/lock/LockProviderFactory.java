package com.zhaofujun.nest.lock;


import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.context.event.channel.MessageChannelProvider;

public class LockProviderFactory {

    public static LockProvider create(){
        return create(NestApplication.current().getLockConfiguration().getProvider());
    }
    public static LockProvider create(String code) {
        return NestApplication.current().getProviderManage().getLockProvider(code);
    }
}
