package com.mf.config;

import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class ZkSerializer implements org.I0Itec.zkclient.serialize.ZkSerializer {
    @Override
    public byte[] serialize(Object o) throws ZkMarshallingError {
        return String.valueOf(o).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public Object deserialize(byte[] bytes) throws ZkMarshallingError {
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
