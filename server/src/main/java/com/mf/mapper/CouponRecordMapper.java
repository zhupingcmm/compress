package com.mf.mapper;

import com.mf.model.CouponRecordDo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CouponRecordMapper {
    int insertCouponRecord(CouponRecordDo couponRecordDo);
}
