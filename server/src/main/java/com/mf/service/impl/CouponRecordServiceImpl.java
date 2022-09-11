package com.mf.service.impl;

import com.common.base.ResponseEnum;
import com.common.exception.CompressException;
import com.mf.dto.CouponRecordDto;
import com.mf.mapper.CouponMapper;
import com.mf.mapper.CouponRecordMapper;
import com.mf.model.CouponDo;
import com.mf.model.CouponRecordDo;
import com.mf.service.CouponRecordService;

import com.mf.utils.ObjectTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.common.base.Asset;
@Service
@RequiredArgsConstructor
public class CouponRecordServiceImpl implements CouponRecordService {

    private final CouponRecordMapper couponRecordMapper;
    private final CouponMapper couponMapper;

    @Override
    public boolean receiveCoupon(CouponRecordDto couponRecordDto) {
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
        couponDo.setTakeCount(newTakeCount);
        result = couponMapper.updateCouponTakeCount(couponDo);
        return Asset.singleRowAffected(result);
    }
}
