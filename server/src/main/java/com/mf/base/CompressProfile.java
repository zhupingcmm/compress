package com.mf.base;

import com.common.base.BaseBean;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompressProfile extends BaseBean {
    private int height;
    private int width;
    private double angle;
}
