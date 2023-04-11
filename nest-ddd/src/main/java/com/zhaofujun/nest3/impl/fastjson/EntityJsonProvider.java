package com.zhaofujun.nest3.impl.fastjson;

import com.zhaofujun.nest3.application.provider.JsonProvider;
import com.zhaofujun.nest3.model.Entity;
import com.zhaofujun.nest3.utils.StringUtils;

import java.lang.reflect.Type;

public class EntityJsonProvider implements JsonProvider<Entity> {

    @Override
    public String toJsonString(Entity object) {
        if (!StringUtils.isEmpty(object.getBeginSnapshot())) return object.getBeginSnapshot();
        return FastjsonUtils.toJsonString(object, null);
    }

    @Override
    public Entity parseObject(String text, Type type) {
        return FastjsonUtils.parseObject(text, type);
    }

    @Override
    public String getCode() {
        return "EntityJsonProvider";
    }
}
