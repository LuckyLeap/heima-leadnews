package com.heima.utils.common;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;

public class ProtostuffUtil {

    // Schema 缓存，避免重复获取
    private static final ConcurrentHashMap<Class<?>, Schema<?>> SCHEMA_CACHE = new ConcurrentHashMap<>();

    /**
     * 序列化
     */
    public static <T> byte[] serialize(T t) {
        Schema schema = getSchema(t.getClass());
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        return ProtostuffIOUtil.toByteArray(t, schema, buffer);
    }

    /**
     * 反序列化
     */
    public static <T> T deserialize(byte[] bytes, Class<T> c) {
        if (bytes == null || bytes.length == 0) {
            return null; // 或者抛出自定义异常
        }

        try {
            Constructor<T> constructor = c.getDeclaredConstructor();
            constructor.setAccessible(true);
            T t = constructor.newInstance();

            Schema<T> schema = getSchema(c);
            ProtostuffIOUtil.mergeFrom(bytes, t, schema);
            return t;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new RuntimeException("Protostuff反序列化失败", e);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> Schema<T> getSchema(Class<T> clazz) {
        return (Schema<T>) SCHEMA_CACHE.computeIfAbsent(clazz, RuntimeSchema::getSchema);
    }
}