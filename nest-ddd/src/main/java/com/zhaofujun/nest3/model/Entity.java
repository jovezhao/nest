package com.zhaofujun.nest3.model;

import com.zhaofujun.nest3.application.context.SnapshotCreator;

import javax.validation.NoProviderFoundException;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Objects;

public abstract class Entity<T extends Identifier> extends DomainObject {
    protected T id;

    public Entity() {
    }

    public Entity(T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }

    private transient String beginSnapshot = "";
    private transient String endSnapshot = "";
    //版本号
    protected int _version;

    public void ready(SnapshotCreator snapshotCreater) {
        snapshotCreater.each(this, (p, q) -> {
            p.beginSnapshot = q;
        });
    }

    public void end(SnapshotCreator snapshotCreater) {
        snapshotCreater.each(this, (p, q) -> {
            p.endSnapshot = q;
        });
    }

    public String getBeginSnapshot() {
        return beginSnapshot;
    }

    public String getEndSnapshot() {
        return endSnapshot;
    }

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
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return Objects.equals(this.id, ((Entity<?>) obj).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, getClassName());
    }
}
