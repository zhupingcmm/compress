package com.mf.config;

import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class ZkSerializer implements org.I0Itec.zkclient.serialize.ZkSerializer {
    @Override
    public byte[] serialize(Object data) throws ZkMarshallingError {
        return String.valueOf(data).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public Object deserialize(byte[] bytes) throws ZkMarshallingError {
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
