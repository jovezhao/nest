package com.zhaofujun.nest.core;

import com.zhaofujun.nest.context.model.Role;

import java.util.Set;

public interface RoleRepository<T extends Role> extends Repository<T> {

    Set<Identifier> getRoleIds(Identifier actorId);
    Identifier getActorIdByRoleId(Identifier roleId);
}
