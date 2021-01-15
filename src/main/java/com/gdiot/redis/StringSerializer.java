package com.gdiot.redis;

import java.nio.charset.Charset;

/**
 * @author ZhouHR
 * @date 2021/01/12
 */
public class StringSerializer implements Serializer {

    private final Charset charset = Charset.forName("UTF8");

    @Override
    public byte[] serialize(Object s) {

        return (s == null || !(s instanceof String) ? null : ((String) s).getBytes(charset));
    }

    @Override
    @SuppressWarnings("unchecked")
    public String deserialize(byte[] bytes) {

        return (bytes == null ? null : new String(bytes, charset));
    }
}
