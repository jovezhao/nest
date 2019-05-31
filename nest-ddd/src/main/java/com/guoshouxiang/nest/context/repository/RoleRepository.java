package com.guoshouxiang.nest.context.repository;

import com.guoshouxiang.nest.context.model.BaseRole;
import com.guoshouxiang.nest.context.model.Identifier;

import java.util.Set;

public interface RoleRepository<T extends BaseRole> extends Repository<T> {
    /**
     *
     * @param actorId
     * @return
     */
    Set<Identifier> getRoleIds(Identifier actorId);
    Identifier getActorIdByRoleId(Identifier roleId);
}
