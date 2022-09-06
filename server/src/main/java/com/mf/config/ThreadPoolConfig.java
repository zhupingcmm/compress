package com.mf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadPoolConfig {
    @Bean
    public ThreadPoolExecutor threadPoolExecutor () {
        return new ThreadPoolExecutor(
                5, 10,
                10, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>()
        );
    }

    @Bean
    public ScheduledThreadPoolExecutor scheduledThreadPoolExecutor () {
        return new ScheduledThreadPoolExecutor(5);
    }
}
