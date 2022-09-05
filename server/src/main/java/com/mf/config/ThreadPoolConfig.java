package com.mf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class ThreadPoolConfig {
    @Bean
    public ThreadPoolExecutor threadPoolExecutor () {
        return new ThreadPoolExecutor(
                5, 10,
                10, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>()
        );
    }
}
