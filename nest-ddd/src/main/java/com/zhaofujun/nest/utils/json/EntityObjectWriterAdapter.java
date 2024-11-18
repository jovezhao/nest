package com.zhaofujun.nest.utils.json;

import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.writer.ObjectWriter;
import com.zhaofujun.nest.ddd.Entity;
import com.zhaofujun.nest.ddd.Identifier;

import java.lang.reflect.Type;

public class EntityObjectWriterAdapter<T extends Entity<? extends Identifier>> implements ObjectWriter<T> {

    private ObjectWriter<T> objectWriter;

    public void setObjectWriter(ObjectWriter<T> objectWriter) {
        this.objectWriter = objectWriter;
    }

    @Override
    public void write(JSONWriter jsonWriter, Object object, Object fieldName, Type fieldType, long features) {

        /**
         *
         * 根据jsonWriter.eleve()级别，
         * 如果当前级别为顶级level==0，使用默认方式生成json
         * 如果当前级别为非顶级level>0，则使用简单模式，只保留id与类型即可。
         *
         */
        @SuppressWarnings("unchecked")
        Entity<? extends Identifier> entity = (Entity<? extends Identifier>) object;
        if (jsonWriter.level() > 0) {
            jsonWriter.startObject();
            writeKeyValue(jsonWriter, "@type", entity.getClassName());
            writeKeyValue(jsonWriter, "id", entity.getId());
            writeKeyValue(jsonWriter, "__shorthand__", true);
            jsonWriter.endObject();

        } else {
            objectWriter.write(jsonWriter, object, fieldName, fieldType, features);
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
