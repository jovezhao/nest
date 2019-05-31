package com.guoshouxiang.nest.context.repository;


import com.guoshouxiang.nest.container.BeanFinder;
import com.guoshouxiang.nest.context.ServiceContext;
import com.guoshouxiang.nest.context.model.BaseEntity;


public class RepositoryFactory {

    public static Repository create(Class entityClass) {

        if (entityClass.equals(BaseEntity.class)) return new DefaultRepository();

        BeanFinder beanFinder = ServiceContext.getCurrent().getBeanFinder();
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

