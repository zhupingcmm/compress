package com.mf.mapper;

import com.mf.model.CouponDo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CouponMapper {
    CouponDo selectCouponById(Long id);

    int updateCouponTakeCount(CouponDo couponDO);
}
