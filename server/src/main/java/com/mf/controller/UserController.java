package com.mf.controller;

import com.common.base.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @GetMapping
    public BaseResponse<String> getUser(){
        log.info("request the user interface");
        return BaseResponse.success("zp");
    }
}
