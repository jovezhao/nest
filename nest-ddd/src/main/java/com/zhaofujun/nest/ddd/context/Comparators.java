package com.zhaofujun.nest.ddd.context;

import java.util.Comparator;

import com.zhaofujun.nest.ddd.Entity;
import com.zhaofujun.nest.ddd.Identifier;
import com.zhaofujun.nest.ddd.Repository;

public class Comparators {

    private static RepositoryComparator repositoryComparator = new RepositoryComparator();
    private static EntityComparator entityComparator = new EntityComparator();

    public static RepositoryComparator getRepositoryComparator() {
        return repositoryComparator;
    }

    public static EntityComparator getEntityComparator() {
        return entityComparator;
    }

    public static class RepositoryComparator implements Comparator<Repository> {

        @Override
        public int compare(Repository o1,
                Repository o2) {
            if (o1 == null && o2 != null)
                return -1;
            if (o1 != null && o2 == null)
                return 1;
            if (o1 == null && o2 == null)
                return 0;
            return o1.hashCode() - o2.hashCode();
        }

    }

    public static class EntityComparator implements Comparator<Entity> {

        @Override
        public int compare(Entity o1, Entity o2) {
            if (o1 == null && o2 != null)
                return -1;
            if (o1 != null && o2 == null)
                return 1;
            if (o1 == null && o2 == null)
                return 0;
            return o1.getId().toValue().compareTo(o2.getId().toValue());
        }
    }
}