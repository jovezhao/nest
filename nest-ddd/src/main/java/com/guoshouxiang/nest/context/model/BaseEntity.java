package com.guoshouxiang.nest.context.model;

import com.guoshouxiang.nest.context.EntityFactory;
import com.guoshouxiang.nest.context.ServiceContext;
import com.guoshouxiang.nest.NullException;
import com.guoshouxiang.nest.context.loader.EntityLoader;
import com.guoshouxiang.nest.utils.EntityUtils;

import java.io.Serializable;

public abstract class BaseEntity<T extends Identifier> implements Serializable {
    private T id;

    public T getId() {
        return id;
    }


    public <U extends BaseRole> U act(Class<U> clazz, Identifier identifier) {
        if (identifier == null)
            throw new NullException("角色的ID不能为空");

        U u = EntityFactory.load(clazz, identifier);
        if (u == null) {
            u = EntityFactory.create(clazz, identifier);
            if (BaseRole.class.isInstance(u)) {
                EntityUtils.setValue(BaseRole.class, u, "actor", this);
            }
        }
        return u;
    }

    public <U extends BaseRole> U act(Class<U> clazz) {
        return act(clazz, this.getId());
    }


    private boolean _represented;

    private boolean _loading;

    private void addToUnitOfWork() {
        if (verify()) {
            ServiceContext.getCurrent()
                    .getContextUnitOfWork().addEntityObject(this);
        } else {
            throw new VerifyFailedException("验证实体失败");
        }
    }


    protected boolean verify() {
        return true;
    }

    public void delete(){
        ServiceContext.getCurrent()
                .getContextUnitOfWork().removeEntityObject(this);
    }
}

