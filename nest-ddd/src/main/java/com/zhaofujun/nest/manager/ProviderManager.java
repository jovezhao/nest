package com.zhaofujun.nest.manager;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import com.zhaofujun.nest.provider.Provider;

public class ProviderManager {
    private static List<Provider> providerList = new CopyOnWriteArrayList<>();

    public static void addProvider(Collection<Provider> providers) {
        providerList.addAll(providers);
    }
    public static void addProvider(Provider... providers) {
        providerList.addAll(Arrays.asList(providers));
    }

   

    public static <T extends Provider> T get(Class<T> tClass, String code) {
        return (T) providerList
                .stream()
                .filter(p -> tClass.isAssignableFrom(p.getClass()) && p.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

    public static <T extends Provider> List<T> getList(Class<T> tClass) {
        return providerList.stream()
                .filter(p -> tClass.isAssignableFrom(p.getClass()))
                .map(p -> (T) p)
                .collect(Collectors.toList());
    }
}
