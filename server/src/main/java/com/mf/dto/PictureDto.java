package com.mf.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PictureDto implements Serializable {
    private Long id;
    private String uid;
    private Long userId;
    private Long  compressSettingId;
    private String type;
    private String name;
    private Long size;
    private byte[] data;
    private Date createTime;
    private Date updateTime;
}
