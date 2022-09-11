package com.mf.service;

import com.mf.dto.CouponRecordDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CouponServiceTest {

    @Autowired
    private CouponRecordService couponRecordService;


    @Test
    public void testReceiveCoupon() {
        int size = 100;
        CountDownLatch countDownLatch = new CountDownLatch(size);
        for (int i = 0; i < size; i++) {
            CouponRecordDto couponRecordDto = CouponRecordDto.builder()
                    .couponId(1L)
                    .userId((long) i)
                    .build();
            new Thread(() -> {
                countDownLatch.countDown();
                try {
                    countDownLatch.await();
                    couponRecordService.receiveCoupon(couponRecordDto);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }

    }
}
