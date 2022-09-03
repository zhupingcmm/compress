package com.common.base;


import lombok.Getter;

import javax.net.ssl.HttpsURLConnection;


public enum ResponseEnum {
    SUCCESS(200,0, "请求成功"),
    ERROR(500,1000, "系统错误"),
    TOO_MANY_ROWS_AFFECTED(500,1001,"更新到其他数据"),
    NO_ROWS_AFFECTED(500,1002, "数据未更新成功"),
    ENTITY_NOT_FOUND(500,1003, "数据不存在"),
    TRANSFORM_EXCEPTION(500,1004, "对象转换异常"),
    SYSTEM_BUSY(500,1005, "系统繁忙"),
    FAILED_UPLOAD(500,1006, "上传失败"),
    FAILED_DOWNLOAD(500,1007, "下载失败"),
    NOT_TOTAL_ROWS_AFFECTED(500,1008,"数据查询或者更新不全"),

    DUPLICATE_FILE(500, 1009, "文件已经存在"),
    TOKEN_EXPIRED(500, 1010, "token 过期"),
    INVALID_TOKEN(500, 1011, "无效 token"),

    AUTHENTICATION_FAILED(403, 1012, "认证失败");



    @Getter
    private int code;
    @Getter
    private String message;

    @Getter
    private int httpCode;

    ResponseEnum(int httpCode, int code, String message) {
        this.httpCode = httpCode;
        this.code = code;
        this.message = message;
    }


}
