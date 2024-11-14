package com.zhaofujun.nest.manager;



import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.zhaofujun.nest.ddd.Entity;
import com.zhaofujun.nest.ddd.Repository;


public class RepositoryManager {
    

    private static Map<Class, Repository> repositoryMap = new HashMap<>();

    public static void addRepository(Repository... repositories) {
        addRepository(Arrays.asList(repositories));
    }

    public static void addRepository(Collection<Repository> repositories) {
        repositories.forEach(repository -> {
            repositoryMap.put(repository.getEntityClass(), repository);
        });
    }

    public static <T extends Entity> Repository<T> getRepository(Class entityClass) {

        // if (entityClass.equals(Entity.class)) return Repository.getDefaulRepository();

        Repository repository = repositoryMap.get(entityClass);

        if (repository == null)
            return getRepository(entityClass.getSuperclass());


        return repository;
    }

}

