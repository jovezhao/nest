package com.zhaofujun.nest.context.repository;


import com.zhaofujun.nest.context.ServiceContextManager;
import com.zhaofujun.nest.core.BeanFinder;
import com.zhaofujun.nest.context.ServiceContext;
import com.zhaofujun.nest.context.model.Entity;
import com.zhaofujun.nest.core.Repository;


public class RepositoryFactory {

    public static Repository create(Class entityClass) {

        if (entityClass.equals(Entity.class)) return new DefaultRepository();

        BeanFinder beanFinder = ServiceContextManager.getCurrent().getApplication().getBeanFinder();
        Repository repository = beanFinder.getInstances(Repository.class)
                .stream()
                .filter(p -> p.getEntityClass().equals(entityClass))
                .findFirst()
                .orElse(null);

        if (repository == null)
            return create(entityClass.getSuperclass());


        return repository;
    }

}

