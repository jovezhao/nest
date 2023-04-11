package com.zhaofujun.nest3.application.manager;


import com.zhaofujun.nest3.application.NestApplication;
import com.zhaofujun.nest3.application.provider.Provider;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class ProviderManage {

    private List<Provider> providerList = new CopyOnWriteArrayList<>();

    public ProviderManage(NestApplication nestApplication) {

    }

    public void addProvider(Provider... providers) {
        providerList.addAll(Arrays.asList(providers));
    }

    public void addProvider(Collection<Provider> providers) {
        providerList.addAll(providers);
    }

    public <T extends Provider> T get(Class<T> tClass, String code) {
        return (T) providerList
                .stream()
                .filter(p -> tClass.isAssignableFrom(p.getClass()) && p.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

    public <T extends Provider> List<T> getList(Class<T> tClass) {
        return providerList.stream()
                .filter(p -> tClass.isAssignableFrom(p.getClass()))
                .map(p -> (T) p)
                .collect(Collectors.toList());
    }

}
