package com.zhaofujun.nest.context.loader;

import com.zhaofujun.nest.cache.CacheClient;
import com.zhaofujun.nest.cache.CacheClientFactory;
import com.zhaofujun.nest.standard.Entity;
import com.zhaofujun.nest.standard.EntityLoader;
import com.zhaofujun.nest.standard.Identifier;
import com.zhaofujun.nest.utils.EntityCacheUtils;

public class CacheEntityLoader<T extends Entity> implements EntityLoader<T> {
    private Class tClass;
    private CacheClient cacheClient;


    public CacheEntityLoader(Class tClass) {

        this.tClass = tClass;
        this.cacheClient = CacheClientFactory.getCacheClient(EntityCacheUtils.getCacheCode());
    }

    @Override
    public T create(Identifier id) {
        return create(tClass, id);
    }

    @Override
    public <U extends T> U create(Class uClass, Identifier id) {

        String cacheKey = EntityCacheUtils.getCacheKey(uClass, id);
        U u = cacheClient.get(uClass, cacheKey);
        return u;
    }


//    public BaseEntity toEntityObject(BaseEntity entityObject) {
//
//        if (entityObject == null) return null;
//
//        if (EntityUtils.isRepresented(entityObject)) return entityObject;
//
//        BaseEntity result = EntityCreate.create(entityObject.getClass(), entityObject.getId(), false, true);
//        List<Field> fields = copyObjectAndFilterDomainObjectField(entityObject, result);//复制对象，并且找出需要处理的字段
//        fields.forEach(p -> {
//            setDomainObject(entityObject, p);
//        });
//
//        EntityUtils.setLoading(result, false);
//        return result;
//    }
//
//
//    private void replaceValueObject(BaseValueObject valueObject) {
//        if (valueObject == null) return;
//        getAllField(valueObject.getClass())
//                .stream()
//                .filter(p -> DomainObject.class.isAssignableFrom(p.getType()))
//                .forEach(p -> {
//                    setDomainObject(valueObject, p);
//                });
//    }
//
//    private void setDomainObject(DomainObject entityObject, Field p) {
//        if (BaseEntity.class.isAssignableFrom(p.getType())) {
//            //如果是实体，使用仓储加载实体
//            BaseEntity source = (BaseEntity) getFieldValue(p, entityObject);
//            if (source != null) {
//                BaseEntity value = new RepositoryEntityLoader<>(p.getType()).create(source.getId());
//                setFieldValue(entityObject, p, value);
//            }
//        }
//        if (BaseValueObject.class.isAssignableFrom(p.getType())) {
//            //如果是值对象，
//            BaseValueObject valueObject = (BaseValueObject) getFieldValue(p, entityObject);
//            replaceValueObject(valueObject);
//        }
//    }
//
//    private void copyField(Field field, Object source, Object target) {
//        try {
//            if (source == null) return;
//            field.setAccessible(true);
//            field.setCurrent(target, field.get(source));
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private List<Field> getAllField(Class tClass) {
//        List<Field> fieldList = new ArrayList<>();
//        Class aClass = tClass;
//        while (aClass != null) {
//            Field[] fields = aClass.getDeclaredFields();
//            fieldList.addAll(Arrays.asList(fields));
//            aClass = aClass.getSuperclass();
//        }
//        return fieldList;
//    }
//
//    private Object getFieldValue(Field field, Object source) {
//        try {
//            field.setAccessible(true);
//            return field.get(source);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    private void setFieldValue(Object object, Field field, Object value) {
//        try {
//            field.setAccessible(true);
//            field.setCurrent(object, value);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private List<Field> copyObjectAndFilterDomainObjectField(Object source, Object target) {
//        List<Field> fieldList = new ArrayList<>();
//        List<Field> result = new ArrayList<>();
//        Class aClass = source.getClass();
//        while (aClass != null) {
//            Field[] fields = aClass.getDeclaredFields();
//            fieldList.addAll(Arrays.asList(fields));
//            aClass = aClass.getSuperclass();
//        }
//        fieldList.forEach(p -> {
//            if (Modifier.isStatic(p.getModifiers()) || Modifier.isFinal(p.getModifiers()))
//                return;
//            if (p.getName().startsWith("_"))
//                return;
//
//            copyField(p, source, target);
//
//            if (DomainObject.class.isAssignableFrom(p.getType())) {
//                result.add(p);
//            }
//
//            if (p.getType().isArray()) {
//
//                Object[] fieldValue = (Object[]) getFieldValue(p, source);
//                if(fieldValue==null) return;
//                for (int i = 0; i < fieldValue.length; i++) {
//                    if (BaseEntity.class.isAssignableFrom(fieldValue[i].getClass())) {
//                        fieldValue[i] = toEntityObject((BaseEntity) fieldValue[i]);
//                    }
//                    if (BaseValueObject.class.isAssignableFrom(fieldValue[i].getClass())) {
//                        replaceValueObject((BaseValueObject) fieldValue[i]);
//                    }
//                }
//                return;
//            }
//            if (List.class.isAssignableFrom(p.getType())) {
//                List fieldValues = (List) getFieldValue(p, source);
//                if(fieldValues==null) return;
//                for (int i = 0; i < fieldValues.size(); i++) {
//                    Object o = fieldValues.get(i);
//                    if (BaseEntity.class.isAssignableFrom(o.getClass())) {
//                        fieldValues.setCurrent(i, toEntityObject((BaseEntity) o));
//                    }
//                    if (BaseValueObject.class.isAssignableFrom(o.getClass())) {
//                        replaceValueObject((BaseValueObject) o);
//                    }
//                }
//                return;
//            }
//            if (Set.class.isAssignableFrom(p.getType())) {
//                Set fieldValues = (Set) getFieldValue(p, source);
//                if(fieldValues==null) return;
//                Object setCurrent = fieldValues.stream().map(q -> {
//                    if (BaseEntity.class.isAssignableFrom(q.getClass())) {
//                        return toEntityObject((BaseEntity) q);
//                    }
//                    if (BaseValueObject.class.isAssignableFrom(q.getClass())) {
//                        replaceValueObject((BaseValueObject) q);
//                        return (BaseValueObject) q;
//                    }
//                    return q;
//                }).collect(Collectors.toSet());
//                setFieldValue(target, p, setCurrent);
//                return;
//            }
//        });
//        return result;
//    }

}
