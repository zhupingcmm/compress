package com.mf.service.impl;

import com.common.base.ResponseEnum;
import com.common.exception.CompressException;
import com.mf.dto.CouponRecordDto;
import com.mf.lock.RedisLock;
import com.mf.mapper.CouponMapper;
import com.mf.mapper.CouponRecordMapper;
import com.mf.model.CouponDo;
import com.mf.model.CouponRecordDo;
import com.mf.service.CouponRecordService;

import com.mf.utils.ObjectTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.common.base.Asset;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponRecordServiceImpl implements CouponRecordService {

    private final CouponRecordMapper couponRecordMapper;
    private final CouponMapper couponMapper;
    private final RedisLock redisLock;

    ReentrantLock reentrantLock = new ReentrantLock();

    @Override
//    @Transactional
    public boolean  receiveCoupon(CouponRecordDto couponRecordDto) {
        try {
            reentrantLock.lock();
            long couponId = couponRecordDto.getCouponId();

            CouponDo couponDo = couponMapper.selectCouponById(couponId);
            Asset.notNull(couponDo);

            /** 判断是否超过发券数量*/
            long newTakeCount = couponDo.getTakeCount() + 1;
            if (newTakeCount > couponDo.getQuota()) {
                throw new CompressException(ResponseEnum.COUPON_NOT_ENOUGH);
            }

            /** 新增优惠券领取记录*/
            couponRecordDto.setStatus(0);
            CouponRecordDo couponRecordDo = ObjectTransformer.transform(couponRecordDto, CouponRecordDo.class);
            int result = couponRecordMapper.insertCouponRecord(couponRecordDo);
            Asset.singleRowAffected(result);

            /** 更新优惠券已领取数量 */
            log.info("newTakeCount is: {}", newTakeCount);
            couponDo.setTakeCount(newTakeCount);
            result = couponMapper.updateCouponTakeCount(couponDo);
            return Asset.singleRowAffected(result);
        } finally {
            reentrantLock.unlock();
        }

    }
}
