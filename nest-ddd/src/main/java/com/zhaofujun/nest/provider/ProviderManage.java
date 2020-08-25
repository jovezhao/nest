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
import com.zhaofujun.nest.context.event.store.MessageStore;

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

    public MessageStore getMessageStore(String code) {
        MessageStore messageStore = get(MessageStore.class, code);

        if (messageStore == null) {
            messageStore = new DefaultMessageStore();
            providerList.add(messageStore);
        }
        return messageStore;
    }

    public MessageConverter getMessageConverter(String code) {
        MessageConverter messageConverter = get(MessageConverter.class, code);

        if (messageConverter == null) {
            messageConverter = new DefaultMessageConverter();
            providerList.add(messageConverter);
        }
        return messageConverter;
    }

    public MessageResendStore getMessageResendStore(String code) {
        MessageResendStore messageResendStore = get(MessageResendStore.class, code);

        if (messageResendStore == null) {
            messageResendStore = new DefaultMessageResendStore();
            providerList.add(messageResendStore);
        }
        return messageResendStore;
    }
}