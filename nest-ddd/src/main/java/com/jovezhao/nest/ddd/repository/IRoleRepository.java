package com.jovezhao.nest.ddd.repository;

import com.jovezhao.nest.ddd.BaseRole;
import com.jovezhao.nest.ddd.Identifier;

import java.util.Set;

public interface IRoleRepository<T extends BaseRole> {
    Set<Identifier> getRoleIds(Identifier actorId);
    Identifier getActorIdByRoleId(Identifier roleId);
}
