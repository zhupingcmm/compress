package com.mf.exception;

import com.common.base.BaseResponse;
import com.common.base.ResponseEnum;
import com.common.exception.CompressException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public BaseResponse exceptionHandler(Exception exception, HttpServletResponse response) {
        response.setStatus(500);
        log.error(exception.getMessage(), exception.getCause());
        if (exception instanceof CompressException) {
            CompressException e = (CompressException) exception;

            ResponseEnum responseEnum = e.getResponseEnum();

            if (responseEnum == null) {
                return new BaseResponse(e.getCode(), e.getMessage());
            } else {
                response.setStatus(responseEnum.getHttpCode());
                return new BaseResponse<>(responseEnum);
            }
        } else {
            return BaseResponse.error(ResponseEnum.ERROR);
        }
    }
}
