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

/**
 * 实体基类
 * 扩展自 DomainObject，用于表示领域模型中的实体对象。
 * 提供了基本的实体操作方法，如创建、删除、验证等。
 */
public abstract class Entity<T extends Identifier> extends DomainObject {
    /**
     * 实体的唯一标识符
     */
    protected T id;

    /**
     * 构造函数
     * 初始化实体的 ID，并在创建实体时发出消息通知。
     *
     * @param id 实体的唯一标识符
     */
    public Entity(T id) {
        this.id = id;
        // 创建实体时，发起消息通知
        MessageUtil.emit(Lifecycle.Entity_New.name(), this);
    }

    /**
     * 获取实体的唯一标识符
     *
     * @return 实体的唯一标识符
     */
    public T getId() {
        return id;
    }

    /**
     * 实体加载完成后的初始化方法
     * 发出消息通知，并保存当前状态的快照。
     */
    public void _ready() {
        // 加载完成后，发起消息通知
        MessageUtil.emit(Lifecycle.Entity_New.name(), this);
        this.beginSnapshot = JsonUtil.toJsonString(this);
    }

    /**
     * 实体结束时的方法
     * 保存当前状态的快照。
     */
    public void _end() {
        this.endSnapshot = JsonUtil.toJsonString(this);
    }

    /**
     * 设置实体的属性值
     *
     * @param fieldName 属性名称
     * @param value 属性值
     */
    public void _setValue(String fieldName, Object value) {
        try {
            Field declaredField = this.getClass().getDeclaredField(fieldName);
            declaredField.setAccessible(true);
            declaredField.set(this, value);
        } catch (Exception ex) {
            // 异常处理
        }
    }

    /**
     * 标记实体是否已删除
     */
    private transient boolean __deleted;

    /**
     * 删除实体
     * 标记实体为已删除状态。
     */
    public void delete() {
        this.__deleted = true;
    }

    /**
     * 检查实体是否已删除
     *
     * @return 实体是否已删除
     */
    public boolean is__deleted() {
        return __deleted;
    }

    /**
     * 实体加载前的状态快照
     */
    private transient String beginSnapshot = "";

    /**
     * 实体加载后的状态快照
     */
    private transient String endSnapshot = "";

    /**
     * 获取实体加载前的状态快照
     *
     * @return 实体加载前的状态快照
     */
    public String getBeginSnapshot() {
        return beginSnapshot;
    }

    /**
     * 获取实体加载后的状态快照
     *
     * @return 实体加载后的状态快照
     */
    public String getEndSnapshot() {
        return endSnapshot;
    }

    /**
     * 实体的版本号
     */
    protected int _version;

    /**
     * 验证实体的有效性
     * 使用默认的验证器进行验证，如果验证失败则抛出异常。
     */
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

    /**
     * 判断两个实体是否相等
     * 主要基于实体的 ID 进行比较。
     *
     * @param obj 要比较的对象
     * @return 是否相等
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        return Objects.equals(this.id, ((Entity<?>) obj).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, getClassName());
    }
}