package com.zhaofujun.nest.context.repository;

import com.zhaofujun.nest.context.model.BaseEntity;
import com.zhaofujun.nest.standard.Repository;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class RepositoryManager {

    private Map<Class, Repository> repositoryMap = new HashMap<>();

    public void addRepository(Repository... repositories) {
        addRepository(Arrays.asList(repositories));
    }

    public void addRepository(Collection<Repository> repositories) {
        repositories.forEach(repository -> {
            repositoryMap.put(repository.getEntityClass(), repository);
        });
    }

    public Repository create(Class entityClass) {

        if (entityClass.equals(BaseEntity.class)) return new DefaultRepository();

        Repository repository = repositoryMap.get(entityClass);

        if (repository == null)
            return create(entityClass.getSuperclass());


        return repository;
    }

}

