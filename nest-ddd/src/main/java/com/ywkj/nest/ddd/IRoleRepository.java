package com.ywkj.nest.ddd;

import java.util.Set;

public interface IRoleRepository<T extends AbstractRole> extends IRepository<T> {
    Set<String> getRoleIds(String actorId);
}
