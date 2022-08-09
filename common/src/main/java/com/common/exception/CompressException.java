package com.common.exception;

import com.common.base.ResponseEnum;
import lombok.Getter;


@Getter
public class CompressException extends RuntimeException{
    private int code;
    private String message;
    private ResponseEnum responseEnum;

    public CompressException(int code, String message){
        this.code = code;
        this.message = message;
    }

    public CompressException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
        this.responseEnum = responseEnum;
    }

}
