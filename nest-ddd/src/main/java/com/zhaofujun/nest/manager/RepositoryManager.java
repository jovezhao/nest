package com.zhaofujun.nest.manager;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.zhaofujun.nest.ddd.Entity;
import com.zhaofujun.nest.ddd.Identifier;
import com.zhaofujun.nest.ddd.Repository;

public class RepositoryManager {

    private static Map<Class, Repository<?, ?>> repositoryMap = new ConcurrentHashMap<>();


    public static void addRepository(Repository<?, ?>... repositories) {
        addRepository(Arrays.asList(repositories));
    }

    public static void addRepository(Collection<Repository> repositories) {
        repositories.forEach(repository -> {
            repositoryMap.put(repository.getEntityClass(), repository);
        });
    }

    public static <T extends Entity<I>, I extends Identifier> Repository<T, I> getRepository(Class<T> tClass) {


        Repository<T, I> repository = (Repository<T, I>) repositoryMap.get(tClass);

        if (repository == null) {
            // 如果当前类没有找到对应的Repository，尝试查找父类的Repository
            //这里会一直找到Entity类的Repository，系统需要默认配置一个EntityRepository
            Class<?> superClass = tClass.getSuperclass();
            if (superClass != null && Entity.class.isAssignableFrom(superClass)) {
                return getRepository((Class<T>) superClass);
            }

        }
        return repository;
    }

}
