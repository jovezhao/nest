package com.jovezhao.nest.ddd;

import java.util.Set;

public interface IRoleRepository<T extends BaseRole> {
    Set<Identifier> getRoleIds(Identifier actorId);
    Identifier getActorIdByRoleId(Identifier roleId);
}
