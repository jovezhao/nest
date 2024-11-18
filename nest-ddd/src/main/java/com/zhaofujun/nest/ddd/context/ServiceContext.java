package com.zhaofujun.nest.ddd.context;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.zhaofujun.nest.Lifecycle;
import com.zhaofujun.nest.ddd.Entity;
import com.zhaofujun.nest.ddd.Identifier;
import com.zhaofujun.nest.ddd.Repository;
import com.zhaofujun.nest.manager.RepositoryManager;
import com.zhaofujun.nest.utils.MessageUtil;
import com.zhaofujun.nest.utils.StringUtil;

public class ServiceContext {

    private Set<Entity> entities = new HashSet<>();

    public void begin() {

    }

    public void submit() {
        entities.forEach(p -> {
            p._end();
        });

        var map = analyse();

        map.forEach((p, q) -> {
            q.forEach((r, s) -> {
                if (s.isEmpty()) {
                    return;
                }
                switch (r) {
                    case create:
                        p.batchInsert(s);
                        MessageUtil.emit(Lifecycle.Entity_Created.name(), s);
                        break;
                    case update:
                        p.batchUpdate(s);
                        MessageUtil.emit(Lifecycle.Entity_Updated.name(), s);
                        break;
                    case remove:
                        p.batchDelete(s);
                        MessageUtil.emit(Lifecycle.Entity_Deleted.name(), s);

                }

            });
        });

    }

    public void end() {

    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    @SuppressWarnings("unchecked")
    public <T extends Entity> T load(Class<T> tClass, Identifier identifier) {
        return (T) entities.stream()
                .filter(p -> p.getId().equals(identifier) && p.getClass().equals(tClass))
                .findFirst()
                .orElse(null);
    }

    private Map<Repository, Map<EntityOperateEnum, Collection<Entity>>> analyse() {

        Map<Repository, Map<EntityOperateEnum, Collection<Entity>>> mapMap = new TreeMap<>(
                Comparators.getRepositoryComparator());

        entities
                .stream()
                .filter(p -> !(StringUtil.isEmpty(p.getBeginSnapshot()) && p.is__deleted())) // 过滤掉没有开始、有删除标识的实体
                .filter(p -> !p.getEndSnapshot().equals(p.getBeginSnapshot())) // 过滤掉开始与结束一样的，即没有发生改变的实体
                .map(p -> EntityInfo.from(p))
                .collect(Collectors.groupingBy(p -> p.getRepository()))
                .forEach((p, q) -> {
                    Map<EntityOperateEnum, Collection<Entity>> collect = q.stream()
                            .collect(Collectors.groupingBy(m -> m.operateEnum, Collectors.mapping(n -> n.getEntity(),
                                    Collectors.toCollection(() -> new TreeSet<>(Comparators.getEntityComparator())))));
                    mapMap.put(p, collect);
                });

        return mapMap;
    }

    static class EntityInfo {
        private Entity<? extends Identifier> entity;
        private EntityOperateEnum operateEnum;
        private Repository<? extends Entity<? extends Identifier>> repository;

        public static EntityInfo from(Entity<? extends Identifier> entity) {

            EntityOperateEnum operateEnum = EntityOperateEnum.create;
            if (StringUtil.isEmpty(entity.getBeginSnapshot()) && !entity.is__deleted())
                operateEnum = EntityOperateEnum.create;
            else if (!StringUtil.isEmpty(entity.getBeginSnapshot()) && entity.is__deleted())
                operateEnum = EntityOperateEnum.remove;
            else if (!StringUtil.isEmpty(entity.getBeginSnapshot()) && !entity.is__deleted()) {
                operateEnum = EntityOperateEnum.update;
            }
            return new EntityInfo(entity, operateEnum, RepositoryManager.getRepository(entity.getClass()));
        }

        private EntityInfo(Entity<? extends Identifier> entity, EntityOperateEnum operateEnum,
                Repository<? extends Entity<? extends Identifier>> repository) {
            this.entity = entity;
            this.operateEnum = operateEnum;
            this.repository = repository;
        }

        public Entity<? extends Identifier> getEntity() {
            return entity;
        }

        public EntityOperateEnum getOperateEnum() {
            return operateEnum;
        }

        public Repository<? extends Entity<? extends Identifier>> getRepository() {
            return repository;
        }

    }

    enum EntityOperateEnum {
        create, update, remove
    }
}
