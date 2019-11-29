package com.zhaofujun.nest.context.model;

import com.zhaofujun.nest.context.ServiceContext;
import com.zhaofujun.nest.NullException;
import com.zhaofujun.nest.context.loader.ConstructEntityLoader;
import com.zhaofujun.nest.context.loader.RepositoryEntityLoader;
import com.zhaofujun.nest.core.EntityFactory;
import com.zhaofujun.nest.core.Identifier;
import com.zhaofujun.nest.utils.EntityUtils;

import java.io.Serializable;

public abstract class Entity<T extends Identifier> implements Serializable {
    protected T id;

    public T getId() {
        return id;
    }


    public <U extends Role> U act(Class<U> clazz, Identifier identifier) {
        if (identifier == null)
            throw new NullException("角色的ID不能为空");
        U u = new RepositoryEntityLoader<U>(clazz).create(identifier);
        if (u == null) {
            u = new ConstructEntityLoader<U>(clazz).create(identifier);
            if (Role.class.isInstance(u)) {
                EntityUtils.setValue(Role.class, u, "actor", this);
            }
        }
        return u;
    }

    public <U extends Role> U act(Class<U> clazz) {
        return act(clazz, this.getId());
    }


    //是否处于加载中状态
    private boolean _loading;

    //是否是新增的对象
    private boolean _newInstance;

    //版本号
    private int _version;

    private void updateEntityObject() {
        if (verify()) {
            ServiceContext.getCurrent()
                    .getContextUnitOfWork().updateEntityObject(this);
        } else {
            throw new VerifyFailedException("验证实体失败");
        }
    }

    private void newEntityObject() {
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

    public void delete() {
        if (!this._newInstance) //  不是新对象的时候才需要调用移除操作
            ServiceContext.getCurrent().getContextUnitOfWork().removeEntityObject(this);
    }

    @Override
    public int hashCode() {
        return EntityUtils.getClassName(this).hashCode() + this.id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (Entity.class.isAssignableFrom(obj.getClass())) {
            Entity entity = (Entity) obj;
            return this.id.equals(entity.id) && EntityUtils.getClassName(entity).equals(EntityUtils.getClassName(this));
        }
        return false;
    }
}

