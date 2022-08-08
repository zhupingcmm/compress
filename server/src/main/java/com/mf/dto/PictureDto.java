package com.mf.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class PictureDto implements Serializable {

    private Long id;
    private Long userId;
    private Long resourceSettingId;
    private String type;
    private String filename;
    private byte[] data;
}
