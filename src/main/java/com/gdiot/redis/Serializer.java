package com.gdiot.redis;

/**
 * @author ZhouHR
 * @date 2021/01/20 19:00
 */
public interface Serializer {

    /**
     * 序列化
     * 
     * @author ZhouHR
     * @date 2021/01/20 19:37
     * @param t
     * @return byte[]
     */
    byte[] serialize(Object t);

    /**
     * 反序列化
     * 
     * @author ZhouHR
     * @date 2021/01/20 19:37
     * @param bytes
     * @return T
     */
    <T> T deserialize(byte[] bytes);
}
