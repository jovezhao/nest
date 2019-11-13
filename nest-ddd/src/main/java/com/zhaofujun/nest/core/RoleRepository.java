package com.zhaofujun.nest.core;

import java.util.Set;

public interface RoleRepository<T extends BaseRole> extends Repository<T> {

    Set<Identifier> getRoleIds(Identifier actorId);
    Identifier getActorIdByRoleId(Identifier roleId);
}
