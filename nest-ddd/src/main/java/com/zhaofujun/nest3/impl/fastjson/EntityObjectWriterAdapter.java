package com.zhaofujun.nest3.impl.fastjson;

import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.writer.ObjectWriter;
import com.zhaofujun.nest3.model.Entity;
import com.zhaofujun.nest3.model.JsonCallback;

import java.lang.reflect.Type;

public class EntityObjectWriterAdapter implements ObjectWriter {

    private ObjectWriter objectWriter;
    private JsonCallback<Entity> jsonCallback;


    public EntityObjectWriterAdapter(ObjectWriter objectWriter, JsonCallback<Entity> jsonCallback) {
        this.objectWriter = objectWriter;
        this.jsonCallback = jsonCallback;
    }

    @Override
    public void write(JSONWriter jsonWriter, Object object, Object fieldName, Type fieldType, long features) {

        /**
         * 这里可以处理遍历实体的行为，可以发起事件，由应用服务上下文监听
         *
         * 根据jsonWriter.eleve()级别，
         * 如果当前级别为顶级level==0，使用默认方式生成json
         * 如果当前级别为非顶级level>0，则使用简单模式，只保留id与类型即可。
         *
         * 如果是顶级对象，直接序列化
         * 如果是非顶级对象，使用简单序列化，并将当前对象加入序列化链条中。方式是回调式。
         *
         */
        Entity entity = (Entity) object;
        if (jsonWriter.level() > 0) {
            jsonWriter.startObject();
            writeKeyValue(jsonWriter, "@type", entity.getClassName());
            writeKeyValue(jsonWriter, "id", entity.getId());
            writeKeyValue(jsonWriter,"__shorthand__",true);
            jsonWriter.endObject();
            FastjsonUtils.toJsonString(entity, jsonCallback);

        } else {
            objectWriter.write(jsonWriter, object, fieldName, fieldType, features);

            if (jsonCallback != null) jsonCallback.callback(entity,jsonWriter.toString());

        }

    }

    private final void writeKeyValue(JSONWriter jsonWriter, String key, Object value) {
        boolean ref = jsonWriter.containsReference(value);
        jsonWriter.writeName(key);
        jsonWriter.writeColon();
        jsonWriter.writeAny(value);
        if (!ref) {
            jsonWriter.removeReference(value);
        }
    }

    private final void writeKeyValue(JSONWriter jsonWriter, String key, String value) {
        jsonWriter.writeName(key);
        jsonWriter.writeColon();
        jsonWriter.writeString(value);
    }

}
