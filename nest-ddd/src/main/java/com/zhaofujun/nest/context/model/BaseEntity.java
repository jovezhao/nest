package com.zhaofujun.nest.context.model;

import com.zhaofujun.nest.exception.VerifyFailedException;
import com.zhaofujun.nest.standard.DomainObject;
import com.zhaofujun.nest.standard.Entity;
import com.zhaofujun.nest.utils.EntityUtils;

import javax.validation.NoProviderFoundException;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.io.Serializable;

public abstract class BaseEntity<T extends AbstractIdentifier> implements Entity<T>, Serializable {
    protected T id;

    public T getId() {
        return id;
    }


    //实体当前状态(1 修改、2 删除)
    private transient boolean __deleted;

    //实体生成方式(1 新增、2 删除）
    private transient boolean __new;

    private transient int __hashcode;

    //版本号
    private int _version;


    public boolean is__deleted() {
        return __deleted;
    }

    public boolean is__new() {
        return __new;
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

    public void delete() {
        this.__deleted = true;
    }


    /**
     * 从数据库或者缓存中读取信息完成后需要调用ready方法，并且记录当时的hash值，
     * 当提交仓储之前再次计算签名对比是否需要进行持久化处理。
     *
     * @return 当前实体的签名值
     */
    public void ready() {
        //计算当前实体hash值
        __hashcode = EntityUtils.getEntityHash(this);
    }

    public boolean isChanged() {
        return __hashcode != EntityUtils.getEntityHash(this);
    }
}

