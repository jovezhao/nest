package com.zhaofujun.nest.context.repository;

import com.zhaofujun.nest.context.model.BaseRole;
import com.zhaofujun.nest.context.model.Identifier;

import java.util.Set;

public interface RoleRepository<T extends BaseRole> extends Repository<T> {

    Set<Identifier> getRoleIds(Identifier actorId);
    Identifier getActorIdByRoleId(Identifier roleId);
}
