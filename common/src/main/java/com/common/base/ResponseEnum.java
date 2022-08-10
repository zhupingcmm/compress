package com.common.base;


import lombok.Getter;


public enum ResponseEnum {
    SUCCESS(0, "请求成功"),
    ERROR(1000, "系统错误"),
    TOO_MANY_ROWS_AFFECTED(1001,"更新到其他数据"),
    NO_ROWS_AFFECTED(1002, "数据未更新成功"),
    ENTITY_NOT_FOUND(1003, "数据不存在"),
    TRANSFORM_EXCEPTION(1004, "对象转换异常"),
    SYSTEM_BUSY(1005, "系统繁忙"),
    FAILED_UPLOAD(1006, "上传失败"),
    FAILED_DOWNLOAD(1007, "下载失败");

    @Getter
    private int code;
    @Getter
    private String message;

    ResponseEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }


}
