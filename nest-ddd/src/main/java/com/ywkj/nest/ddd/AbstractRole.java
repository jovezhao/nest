package com.ywkj.nest.ddd;

import java.io.Serializable;

/**
 * Created by Jove on 2016-03-31.
 */
public abstract class AbstractRole<T extends EntityObject> extends EntityObject {
    private T actor;

    public T getActor() {
        return actor;
    }

    public void setActor(T actor) {
        this.actor = actor;
    }




}
