package com.zhaofujun.nest.provider;

import com.zhaofujun.nest.cache.CacheProvider;
import com.zhaofujun.nest.cache.DefaultCacheProvider;
import com.zhaofujun.nest.context.event.channel.MessageChannelProvider;
import com.zhaofujun.nest.context.event.channel.local.LocalMessageChannel;
import com.zhaofujun.nest.context.event.message.DefaultMessageConverter;
import com.zhaofujun.nest.context.event.message.MessageConverter;
import com.zhaofujun.nest.context.event.resend.DefaultMessageResendStore;
import com.zhaofujun.nest.context.event.resend.MessageResendStore;
import com.zhaofujun.nest.context.event.store.DefaultMessageStore;
import com.zhaofujun.nest.context.event.store.MessageStoreProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ProviderManage {

    private List<Provider> providerList = new ArrayList<>();

    public void addProvider(Provider... providers) {
        providerList.addAll(Arrays.asList(providers));
    }

    public void addProvider(Collection<Provider> providers) {
        providerList.addAll(providers);
    }
    private <T extends Provider> T get(Class<T> tClass, String code) {
        return (T) providerList
                .stream()
                .filter(p -> tClass.isAssignableFrom(p.getClass()) && code.equals(code))
                .findFirst()
                .orElse(null);
    }

    public CacheProvider getCacheProvider(String code) {

        CacheProvider cacheProvider = get(CacheProvider.class, code);

        if (cacheProvider == null) return new DefaultCacheProvider();
        return cacheProvider;
    }

    public MessageChannelProvider getMessageChannel(String code) {

        MessageChannelProvider channelProvider = get(MessageChannelProvider.class, code);

        if (channelProvider == null) return new LocalMessageChannel();
        return channelProvider;
    }

    public MessageStoreProvider getMessageStore(String code) {
        MessageStoreProvider messageStoreProvider = get(MessageStoreProvider.class, code);

        if (messageStoreProvider == null) return new DefaultMessageStore();
        return messageStoreProvider;
    }

    public MessageConverter getMessageConverter(String code) {
        MessageConverter messageConverter = get(MessageConverter.class, code);

        if (messageConverter == null) return new DefaultMessageConverter();
        return messageConverter;
    }

    public MessageResendStore getMessageResendStore(String code) {
        MessageResendStore messageResendStore = get(MessageResendStore.class, code);

        if (messageResendStore == null) return new DefaultMessageResendStore();
        return messageResendStore;
    }
}
