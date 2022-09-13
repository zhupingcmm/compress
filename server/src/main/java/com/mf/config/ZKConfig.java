package com.mf.config;

import org.I0Itec.zkclient.ZkClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ZKConfig {

    @Bean
    public ZkClient createZkClint(){
        ZkClient zkClient = new ZkClient("localhost:2181");
        zkClient.setZkSerializer(new ZkSerializer());
        return zkClient;
    }
}
