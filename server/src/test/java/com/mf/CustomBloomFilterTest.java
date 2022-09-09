package com.mf;

import lombok.RequiredArgsConstructor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomBloomFilterTest {
    private int size = 100;

    private static final String BLOOM_FILTER_NAME = "bloomFilter";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Before
    public void init() {
        for (int i = 0; i < size; i++) {
            redisTemplate.opsForValue().setBit(BLOOM_FILTER_NAME, getOffset(i),true);
        }
    }

    @Test
    public void testCustomBloomFilter() {
        int count = 0;
        for (int j = size; j < size * 2; j++) {
            boolean match = redisTemplate.opsForValue().getBit(BLOOM_FILTER_NAME, this.getOffset(j));
            if(match) {
                count++;
                System.out.println(j + "误判了");
            }
        }
        System.out.println("误判个数：" + count);
    }

    private long getOffset(int i) {
        long hashCode = Math.abs((BLOOM_FILTER_NAME + i).hashCode());
        long offset = (long) (hashCode % Math.pow(2, 32));
        System.out.println(
                "index =" + i + "\t" +
                        "hashCode =" + hashCode +"\t"+
                        "offset =" + offset
        );
        return offset;
    }
}
