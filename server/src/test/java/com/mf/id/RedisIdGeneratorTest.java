package com.mf.id;

import com.common.base.IdTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisIdGeneratorTest {

    @Autowired
    private RedisIdGenerator redisIdGenerator;

    @Test
    public void getNextTest() {
       long id =  redisIdGenerator.getNext(IdTypeEnum.COMPRESS);
        System.out.println(id);
    }

    @Test
    public void getNextAsyncTest(){
        int size = 10;
        CountDownLatch countDownLatch = new CountDownLatch(size);
        for (int i = 0; i < size; i++) {
            new Thread(() -> {
                try {
                    countDownLatch.await();
                    long id = redisIdGenerator.getNext(IdTypeEnum.COMPRESS);
                    log.info("id is {}", id);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }).start();
            countDownLatch.countDown();
        }
    }
}
