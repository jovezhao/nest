package com.jovezhao.nest.ddd;



/**
 * 实体基类。 当实体与实体之间只存在关联关系时，以实体来标记。 实体在仓储保存时将以引用方式关联，数据库则以主外键关联。
 *
 * @author Jove
 */
public abstract class EntityObject extends BaseEntityObject<StringIdentifier> {


}
