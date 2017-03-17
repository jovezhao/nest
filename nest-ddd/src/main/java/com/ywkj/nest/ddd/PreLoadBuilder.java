package com.ywkj.nest.ddd;

/**
 * 用于仓储预加载时的实体构建器
 * @param <T>
 */
 class PreLoadBuilder<T extends EntityObject> implements IBuilder<T> {
    Class<T> tClass;

    public PreLoadBuilder(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public T build(String id) {
        T t = EntityObjectFactory.createForLoad(tClass);
        t.setId(id);
        return t;
    }
}
