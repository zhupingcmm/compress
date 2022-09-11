package com.mf.lock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisLockTest {

    @Autowired
    private RedisLock redisLock;

    @Test
    public void testRedisLock () {
        int size = 2;
        CountDownLatch countDownLatch = new CountDownLatch(size);
        for (int i = 0; i < size; i++) {
            new Thread(() -> {

                try {
                    countDownLatch.await();
                    redisLock.lock();

//                    Thread.sleep(100);

                    System.out.println(Thread.currentThread().getName() + "：执行完成了业务逻辑");

                    redisLock.unlock();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }, String.format("Thread-%s", i)).start();

            countDownLatch.countDown();
        }
    }
}
