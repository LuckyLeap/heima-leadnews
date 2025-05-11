package com.heima.utils.common;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * jdk序列化
 */
@Slf4j
public class JdkSerializeUtil {

    /**
     * 序列化对象为字节数组
     *
     * @param obj 待序列化的对象，不能为 null
     * @param <T> 泛型类型
     * @return 序列化后的字节数组
     * @throws RuntimeException 如果序列化失败
     */
    public static <T> byte[] serialize(T obj) {
        if (obj == null) {
            throw new NullPointerException("序列化对象不能为 null");
        }

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {

            oos.writeObject(obj);
            return bos.toByteArray();

        } catch (Exception ex) {
            log.error("序列化失败: {}", obj, ex);
            throw new RuntimeException("序列化失败", ex);
        }
    }

    /**
     * 反序列化字节数组为指定类型的对象。
     *
     * @param data  待反序列化的字节数组
     * @param clazz 目标对象的类类型
     * @param <T>   泛型类型
     * @return 反序列化后的对象，失败返回 null
     * @throws RuntimeException 如果反序列化失败或类型不匹配
     */
    public static <T> T deserialize(byte[] data, Class<T> clazz) {
        if (data == null || data.length == 0) {
            throw new IllegalArgumentException("反序列化的字节数组不能为 null 或空");
        }

        try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
             ObjectInputStream ois = new ObjectInputStream(bis)) {

            Object obj = ois.readObject();
            if (!clazz.isInstance(obj)) {
                throw new ClassCastException("反序列化结果类型不匹配: " + obj.getClass() + " 无法转换为 " + clazz);
            }
            return clazz.cast(obj);

        } catch (ClassNotFoundException ex) {
            log.error("找不到目标类: {}, 反序列化失败", clazz.getName(), ex);
            throw new RuntimeException("反序列化失败: 找不到目标类", ex);
        } catch (Exception ex) {
            log.error("反序列化失败", ex);
            throw new RuntimeException("反序列化失败", ex);
        }
    }
}