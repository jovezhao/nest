package com.zhaofujun.nest3.impl.fastjson;

import com.zhaofujun.nest3.model.Entity;
import com.zhaofujun.nest3.model.EntityIterator;
import com.zhaofujun.nest3.model.EntityJsonBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 利用FastJson生成json字符串的时候会遍历所有实体对象的特点，实现json的生成和实体的遍历。
 */
public class FastjsonIteratorAndJsonBuilder implements EntityIterator, EntityJsonBuilder {

    private Map<Entity, String> jsonMap = new HashMap<>();

    private boolean addJson(Entity entity, String jsonString) {
        boolean containsKey = jsonMap.containsKey(entity);
        jsonMap.put(entity, jsonString);
        return containsKey;
    }

    @Override
    public void each(Entity root, Consumer<Entity> consumer) {
        if (!jsonMap.containsKey(root)) {
            //如果一个实体已经存在，则不再重复操作
            FastjsonUtils.toJsonString(root, (entity, jsonString) -> {
                addJson(entity, jsonString);
                consumer.accept(entity);
            });
        }
    }

    @Override
    public String toJsonString(Entity root) {
        if (jsonMap.containsKey(root)) {
            return jsonMap.get(root);
        }
        return FastjsonUtils.toJsonString(root, null);
    }
}

