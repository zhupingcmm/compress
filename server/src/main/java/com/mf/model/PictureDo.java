package com.mf.model;

import com.common.base.BaseBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PictureDo extends BaseBean {
    private Long id;
    private Long userId;
    private Long  compressSettingId;
    private String type;
    private String filename;
    private byte[] data;
    private Date createTime;
    private Date updateTime;
}
