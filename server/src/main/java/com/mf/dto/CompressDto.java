package com.mf.dto;

import com.common.base.BaseBean;
import com.mf.base.CompressProfile;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CompressDto extends BaseBean {

    private List<Long> pictureIds;

    private CompressProfile compressProfile;
}
