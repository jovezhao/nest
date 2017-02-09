package com.ywkj.nest.ddd;

public interface IRoleBuilder<T extends AbstractRole<U>,U extends EntityObject> {
    T build(Class<T> tClass, Class<U> uClass,String id)  ;
}
