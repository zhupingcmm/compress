package com.common.base;

import lombok.Getter;
import lombok.Setter;

public enum IdTypeEnum {
    COMPRESS(1, "compress");

    @Getter
    private int code;

    @Getter
    private String serviceName;

    IdTypeEnum(int code, String serviceName) {
        this.code = code;
        this.serviceName = serviceName;
    }
}
