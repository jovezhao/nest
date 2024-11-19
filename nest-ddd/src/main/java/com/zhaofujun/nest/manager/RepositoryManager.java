package com.zhaofujun.nest.manager;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.zhaofujun.nest.ddd.Entity;
import com.zhaofujun.nest.ddd.Identifier;
import com.zhaofujun.nest.ddd.Repository;

public class RepositoryManager {

    private static Map<Type, Repository<? extends Entity<? extends Identifier>>> repositoryMap = new HashMap<>();

    public static void addRepository(Repository<?>... repositories) {
        addRepository(Arrays.asList(repositories));
    }

    @SuppressWarnings("rawtypes")
    public static void addRepository(Collection<Repository> repositories) {
        repositories.forEach(repository -> {
            repositoryMap.put(repository.getEntityType(), repository);
        });
    }

    public static <T extends Entity<?>> Repository<T> getRepository(Type entityType) {

        // if (entityClass.equals(Entity.class)) return
        // Repository.getDefaulRepository();

        @SuppressWarnings("unchecked")
        Repository<T> repository = (Repository<T>) repositoryMap.get(entityType);

        if (repository == null) {
            Type superType = ((Class)entityType).getSuperclass();
            return getRepository(superType);

        }
        return repository;
    }

}
