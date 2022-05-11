package com.zhaofujun.nest.context.appservice;

import com.zhaofujun.nest.context.model.BaseEntity;
import com.zhaofujun.nest.standard.Repository;

import java.util.Comparator;

public class  Comparators {

    private static RepositoryComparator repositoryComparator=new RepositoryComparator();
    private static BaseEntityComparator baseEntityComparator=new BaseEntityComparator();

    public static RepositoryComparator getRepositoryComparator() {
        return repositoryComparator;
    }

    public static BaseEntityComparator getBaseEntityComparator() {
        return baseEntityComparator;
    }

    public static class RepositoryComparator implements Comparator<Repository> {
        @Override
        public int compare(Repository o1, Repository o2) {
            if (o1 == null && o2 != null) return -1;
            if (o1 != null && o2 == null) return 1;
            if (o1 == null && o2 == null) return 0;
            return o1.getEntityClass().getName().compareTo(o2.getEntityClass().getName());
        }
    }
    public static class BaseEntityComparator implements Comparator<BaseEntity>{

        @Override
        public int compare(BaseEntity o1, BaseEntity o2) {
            if (o1 == null && o2 != null) return -1;
            if (o1 != null && o2 == null) return 1;
            if (o1 == null && o2 == null) return 0;
            return o1.getId().toValue().compareTo(o2.getId().toValue());
        }
    }
}