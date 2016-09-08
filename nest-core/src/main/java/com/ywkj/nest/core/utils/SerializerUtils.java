package com.ywkj.nest.core.utils;

import com.ywkj.nest.core.log.ILog;
import com.ywkj.nest.core.log.LogAdapter;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 对象序列化器，将Java对象序列化为字符串形式，或者相反，从字符串反序列化为对象。 Created by yyang on 14-9-16.
 */
public class SerializerUtils {
    static ILog logger = new LogAdapter(SerializerUtils.class);

    /**
     * 将对象序列化为Json字符串
     *
     * @param anObject 要序列化的对象
     * @return 对象的序列化形式
     */
    public static String serializeToJson(Object anObject) {
        return JSONObject.valueToString(anObject);
    }

    /**
     * 将Json字符串反序列化为对象
     *
     * @param serializedString 对象的字符串序列化形式
     * @param objectClass      对象的类
     * @param <T>              对象的类型
     * @return 一个对象实例
     */
    public static  <T> T deserialize(String serializedString, Class<T> objectClass) {
        Object j = JSONObject.stringToValue(serializedString);
        return (T) j;
    }

    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            // 序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
            logger.fatal(e, object);
        }
        return null;
    }

    public static <T> T deserialize(byte[] bytes, Class<T> clazz) {
        ByteArrayInputStream bais = null;
        try {
            // 反序列化
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            Object obj = ois.readObject();
            return (T) obj;
        } catch (Exception e) {
            logger.fatal(e, clazz);
        }
        return null;
    }
}
