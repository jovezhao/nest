package com.zhaofujun.nest3.application.manager;


import com.zhaofujun.nest3.application.DefaultRepository;
import com.zhaofujun.nest3.application.NestApplication;
import com.zhaofujun.nest3.model.Entity;
import com.zhaofujun.nest3.model.Repository;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class RepositoryManager {
    private NestApplication nestApplication;

    public RepositoryManager(NestApplication nestApplication) {
        this.nestApplication = nestApplication;
    }

    private Map<Class, Repository> repositoryMap = new HashMap<>();

    public void addRepository(Repository... repositories) {
        addRepository(Arrays.asList(repositories));
    }

    public void addRepository(Collection<Repository> repositories) {
        repositories.forEach(repository -> {
            repositoryMap.put(repository.getEntityClass(), repository);
        });
    }

    public Repository getRepository(Class entityClass) {

        if (entityClass.equals(Entity.class)) return new DefaultRepository();

        Repository repository = repositoryMap.get(entityClass);

        if (repository == null)
            return getRepository(entityClass.getSuperclass());


        return repository;
    }

}

