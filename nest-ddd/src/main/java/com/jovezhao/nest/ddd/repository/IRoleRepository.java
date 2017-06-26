package com.jovezhao.nest.ddd.repository;

import com.jovezhao.nest.ddd.BaseRole;
import com.jovezhao.nest.ddd.Identifier;

import java.util.Set;

/**
 * 角色仓储
 * @param <T>
 */
public interface IRoleRepository<T extends BaseRole> extends IRepository<T>{
    /**
     *
     * @param actorId
     * @return
     */
    Set<Identifier> getRoleIds(Identifier actorId);
    Identifier getActorIdByRoleId(Identifier roleId);
}
