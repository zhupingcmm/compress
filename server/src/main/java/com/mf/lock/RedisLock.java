package com.mf.lock;


import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisLock implements Lock {

    private static final String LOCK = "LOCK";

    private static final long TIME = 100L;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @SneakyThrows
    @Override
    public void lock() {
        tryLock(TIME, TimeUnit.SECONDS);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @SneakyThrows
    @Override
    public boolean tryLock() {
        return tryLock(TIME, TimeUnit.SECONDS);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        boolean success = false;
        while (!success) {
            success = Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(LOCK, Thread.currentThread().getName(), time, unit));
        }
        log.info("*** {} get the lock ***", Thread.currentThread().getName());
        return true;
    }

    @Override
    public void unlock() {
        String script = "if redis.call('get',KEYS[1]) == ARGV[1] " +
                "then " +
                "return redis.call('del',KEYS[1]) " +
                "else " +
                "   return 0 " +
                "end";
        RedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);
        Long result = redisTemplate.execute(redisScript, Arrays.asList(LOCK, Thread.currentThread().getName()));
        log.info("*** {} 释放了 ***", Thread.currentThread().getName());
        if (result != 0L) {
            log.info("*** {} 释放了 ***", Thread.currentThread().getName());
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
