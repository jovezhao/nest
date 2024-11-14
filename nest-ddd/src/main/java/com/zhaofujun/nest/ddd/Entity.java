package com.zhaofujun.nest.ddd;

import java.lang.reflect.Field;
import java.util.Objects;

import javax.validation.NoProviderFoundException;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import com.zhaofujun.nest.Lifecycle;
import com.zhaofujun.nest.exception.VerifyFailedException;
import com.zhaofujun.nest.utils.JsonUtil;
import com.zhaofujun.nest.utils.MessageUtil;

public abstract class Entity<T extends Identifier> extends DomainObject {
    protected T id;

    public Entity(T id) {
        this.id = id;
        // 创建实体时，发起消息通知
        MessageUtil.emit(Lifecycle.Entity_New.name(), this);
    }

    public T getId() {
        return id;
    }

    public void _ready() {
        // 加载完成后，发起消息通知
        MessageUtil.emit(Lifecycle.Entity_New.name(), this);
        this.beginSnapshot = JsonUtil.toJsonString(this);
    }

    public void _end() {
        this.endSnapshot = JsonUtil.toJsonString(this);
    }

    public void _setValue(String fieldName, Object value) {
        try {
            Field declaredField = this.getClass().getDeclaredField(fieldName);
            declaredField.setAccessible(true);
            declaredField.set(this, value);
        } catch (Exception ex) {

        }
    }

    private transient boolean __deleted;

    public void delete() {
        this.__deleted = true;
    }

    public boolean is__deleted() {
        return __deleted;
    }

    private transient String beginSnapshot = "";
    private transient String endSnapshot = "";

    public String getBeginSnapshot() {
        return beginSnapshot;
    }

    public String getEndSnapshot() {
        return endSnapshot;
    }

    // 版本号
    protected int _version;

    public void verify() {
        ValidatorFactory validatorFactory;
        try {
            validatorFactory = Validation.buildDefaultValidatorFactory();
        } catch (NoProviderFoundException exception) {
            return;
        }
        Object[] strings = validatorFactory
                .getValidator()
                .validate(this)
                .stream()
                .map(p -> p.getMessage())
                .toArray();

        if (strings.length > 0)
            throw new VerifyFailedException("对象验证失败", strings);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        return Objects.equals(this.id, ((Entity<?>) obj).id);
    }

  
    // @Override
    // public int hashCode() {
    // return Objects.hash(this.id, getClassName());
    // }

}
