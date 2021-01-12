package com.gdiot.redis;

/**
 * @author ZhouHR
 * @date 2021/01/12
 */
public interface Serializer {

    /**
     * 序列化
     */
    byte[] serialize(Object t);

    /**
     * 反序列化
     */
    <T> T deserialize(byte[] bytes);
}
