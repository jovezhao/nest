package com.zhaofujun.nest.ddd.context;

import java.util.*;
import java.util.stream.Collectors;

import com.zhaofujun.nest.Lifecycle;
import com.zhaofujun.nest.ddd.Entity;
import com.zhaofujun.nest.ddd.Identifier;
import com.zhaofujun.nest.ddd.Repository;
import com.zhaofujun.nest.manager.RepositoryManager;
import com.zhaofujun.nest.utils.MessageUtil;
import com.zhaofujun.nest.utils.StringUtil;

public class ServiceContext {

    private Set<Entity<? extends Identifier>> entities = new HashSet<>();

    public void begin() {

    }

    public void submit() {
        entities.forEach(Entity::_end);

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

    public void addEntity(Entity<?> entity) {
        entities.add(entity);
    }

    public <T extends Entity<I>, I extends Identifier> T load(Class<T> tClass, I identifier) {
        Entity<? extends Identifier> entity = entities.stream()
                .filter(p -> p.getId().equals(identifier) && p.getClass().equals(tClass))
                .findFirst()
                .orElse(null);
        return (T) entity;
    }

    public <T extends Entity<? extends I>, I extends Identifier> Map<I, T> load(Class<T> tClass, Collection<I> identifiers) {
        Map<Identifier, T> map = new TreeMap<>();
        Map<? extends Identifier, ? extends Entity<? extends Identifier>> entityMap = entities.stream()
                .filter(p -> p.getClass().equals(tClass))
                .filter(p -> identifiers.contains(p.getId()))
                .collect(Collectors.toMap(Entity::getId, p -> p));
        return (Map<I, T>) entityMap;
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
        private Entity entity;
        private EntityOperateEnum operateEnum;
        private Repository repository;

        public static EntityInfo from(Entity entity) {

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

        private EntityInfo(Entity entity, EntityOperateEnum operateEnum,
                           Repository repository) {
            this.entity = entity;
            this.operateEnum = operateEnum;
            this.repository = repository;
        }

        public Entity getEntity() {
            return entity;
        }

        public EntityOperateEnum getOperateEnum() {
            return operateEnum;
        }

        public Repository getRepository() {
            return repository;
        }

    }

    enum EntityOperateEnum {
        create, update, remove
    }
}
