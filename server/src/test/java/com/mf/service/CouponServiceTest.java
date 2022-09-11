package com.mf.service;

import com.mf.dto.CouponRecordDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CouponServiceTest {

    @Autowired
    private CouponRecordService couponRecordService;


    @Test
    public void testReceiveCoupon() {
        CouponRecordDto couponRecordDto = CouponRecordDto.builder()
                .couponId(1l)
                .userId(1l)
                .build();
        couponRecordService.receiveCoupon(couponRecordDto);
    }
}
